import asyncio
import base64
import time
from io import BytesIO
from threading import Thread
import flask
import pyscreenshot
import websockets


def grab_screen():
    img = pyscreenshot.grab()
    buf = BytesIO()
    img.save(buf, format="JPEG", quality=30)
    return base64.b64encode(buf.getvalue()).decode("utf-8")


async def ws_serve_client(ws: websockets.WebSocketServerProtocol, req_uri):
    print("In ws_serve_client")
    while True:
        before_send = time.time()
        await ws.send(grab_screen())

        sleep_for = max(0, 1 - (time.time() - before_send))
        print("Sleep for {} secs".format(sleep_for))
        await asyncio.sleep(sleep_for)


def start_ws_server():
    # from ctypes import windll
    # user32 = windll.user32
    # user32.SetProcessDPIAware()

    asyncio.set_event_loop(asyncio.new_event_loop())
    asyncio.get_event_loop().run_until_complete(
        websockets.serve(ws_serve_client, "0.0.0.0", 9000)
    )
    asyncio.get_event_loop().run_forever()


def start_flask_app():
    app = flask.Flask(__name__, static_folder="")

    @app.route("/")
    def index():
        return app.send_static_file("client.html")

    @app.route("/<path:path>")
    def static_files(path):
        return flask.send_from_directory("", path)

    app.run(host='0.0.0.0', port=80)


def main():
    Thread(target=start_ws_server).start()
    start_flask_app()


if __name__ == "__main__":
    main()

import socket


if __name__ == "__main__":
    srvsock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    srvsock.bind(("0.0.0.0", 1250))

    while True:
        data, addr = srvsock.recvfrom(4096)
        request = data.decode("utf-8").strip()
        result = 0

        if "+" in request:
            num1, num2 = request.split("+")
            result = int(num1) + int(num2)
        elif "-" in request:
            num1, num2 = request.split("-")
            result = int(num1) - int(num2)

        print("Klienten {}:{} forespurte: {}. Svar: {}".format(addr[0], addr[1], request, result))
        srvsock.sendto((str(result) + "\n").encode("utf-8"), addr)

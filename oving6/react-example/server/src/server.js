// @flow
import express from 'express';
import bodyParser from 'body-parser';
import {Sequelize, Model} from 'sequelize';

let server = express();
const sequelize = new Sequelize("mysql://");

// Serve the React client
server.use(express.static(__dirname + '/../../client'));

// Automatically parse json content
server.use(bodyParser.json());

const Article: Model = sequelize.define("Article", {
    id: {type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true},
    title: {type: Sequelize.TEXT},
    abstract: {type: Sequelize.TEXT},
    text: {type: Sequelize.TEXT},
    category: {type: Sequelize.TEXT},
    votes: {type: Sequelize.INTEGER, defaultValue: 0},
});

const Comment: Model = sequelize.define("Comment", {
    id: {type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true},
    //article: {type: Sequelize.INTEGER},
    text: {type: Sequelize.TEXT}
});

Article.hasMany(Comment, {foreignKey: "article"});

sequelize.sync().then(() => {
    console.log("DB synced");
});
/*
sequelize.sync({force: true}).then(() => {
    Article.create({title: "Test test"}).then(article => {
        console.log("Article inserted: " + article.id + ", title: " + article.title);
    });

    Article.create({title: "Enda en testartikkel! (testikkel?)", abstract: "En testarikkel uten kunst er bare en testikkel."}).then(article => {
        console.log("Article inserted: " + article.id + ", title: " + article.title);
    });
});
*/


// Get all articles
server.get('/articles', (request: express$Request, response: express$Response) => {
    Article.findAll({include: [Comment]}).then(articles => response.send(articles));
});

// Get an article given its id
server.get('/articles/:id', (request: express$Request, response: express$Response) => {
    Article.findById(Number(request.params.id), {include: [Comment]}).then(article => {
        if (article == null) {
            // Respond with not found status code
            response.sendStatus(404);
        } else {
            response.send(article);
        }
    });
});

// Add new article
server.post('/articles', (request: express$Request, response: express$Response) => {
    if (request.body && typeof request.body.title == 'string'
        && typeof request.body.abstract == 'string'
        && typeof request.body.text == 'string'
        && typeof request.body.category == 'string'
    ) {
        Article.create({
            title: request.body.title,
            abstract: request.body.abstract,
            text: request.body.text,
            category: request.body.category
        }).then(article => {
            console.log("Created article " + article.id);
            response.send(article.id.toString()); // Respond with newly generated article id
        });
    } else {
        // Respond with bad request status code
        response.sendStatus(400);
    }
});

server.get("/category/:name", (request: express$Request, response: express$Response) => {
    Article.findAll({
        where: {
            category: request.params.name
        }
    }).then(articles => response.send(articles));
});

// Post comment on article
server.post("/articles/:id/comments", (request: express$Request, response: express$Response) => {
    if (request.body && typeof request.body.text == 'string') {
        Comment.create({
            text: request.body.text,
            article: request.params.id
        }).then(comment => {
            console.log("Created comment " + comment.id);
            response.send(comment.id.toString()); // Respond with newly generated comment id
        });
    } else {
        // Respond with bad request status code
        response.sendStatus(400);
    }
});

server.get("/articles/:id/comments", (request: express$Request, response: express$Response) => {
    Comment.findAll({
        where: {article: request.params.id}
    }).then(comments => {
        response.send(comments);
    })
});

// Upvote article
server.post("/articles/:id/upvote", (request: express$Request, response: express$Response) => {
    Article.findById(Number(request.params.id))
        .then(article => article.increment("votes"))
        .then(() => response.sendStatus(200));
});

// Downvote article
server.post("/articles/:id/downvote", (request: express$Request, response: express$Response) => {
    Article.findById(Number(request.params.id))
        .then(article => article.decrement("votes"))
        .then(() => response.sendStatus(200));
});

// Start the web server at port 3000
server.listen(3000);

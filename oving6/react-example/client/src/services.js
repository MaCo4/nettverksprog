// @flow

export class Comment {
    id: number;
    text: string;
    createdAt: string;
}

export class Article {
    id: number;
    title: string;
    abstract: string;
    text: string;
    category: string;
    votes: number;
    Comments: Comment[];
}

class ArticleService {
    getArticles(): Promise<Article[]> {
        return fetch('/articles').then(response => {
            if (!response.ok) throw new Error(response.statusText);
            return response.json();
        });
    }

    getArticle(id: number): Promise<Article> {
        return fetch('/articles/' + id).then(response => {
            if (!response.ok) throw new Error(response.statusText);
            return response.json();
        });
    }

    addArticle(title: string, abstract: string, text: string, category: string): Promise<number> {
        let body = JSON.stringify({title: title, abstract: abstract, text: text, category: category});
        return fetch('/articles', {
            method: 'POST',
            headers: new Headers({'Content-Type': 'application/json'}),
            body: body
        }).then(response => {
            if (!response.ok) throw new Error(response.statusText);
            return response.json();
        });
    }

    upvoteArticle(id: number): Promise<Response> {
        return fetch("/articles/" + id + "/upvote", {
            method: "POST"
        });
    }

    downvoteArticle(id: number): Promise<Response> {
        return fetch("/articles/" + id + "/downvote", {
            method: "POST"
        });
    }

    postCommentOnArticle(id: number, text: string): Promise<Response> {
        return fetch("/articles/" + id + "/comments", {
            method: "POST",
            headers: new Headers({'Content-Type': 'application/json'}),
            body: JSON.stringify({text: text})
        });
    }

    getArticlesInCategory(category: string): Promise<Article[]> {
        return fetch("/category/" + category).then(res => {
            return res.json();
        });
    }
}
export let articleService = new ArticleService();

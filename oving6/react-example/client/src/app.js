// @flow
import * as React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter, Route, Switch} from 'react-router-dom';
import createHashHistory from 'history/createHashHistory';
const history = createHashHistory();
import {Article, articleService} from './services';
import {Alert, NavigationBar, Card, Table, Form} from './widgets';
import Signal from 'signals';

class Home extends React.Component<{}> {
  render() {
    return <Card title="Articlr">Articlr.io is a place to share articles ✏️📝🤖 emojis buzzwords buzzword etc</Card>;
  }
}

class ArticleDetails extends React.Component<{match: {params: {id: number}}}, {article: ?Article}> {
  state = {article: null};

  comment: ?HTMLTextAreaElement;

  render() {
    if (!this.state.article) return null;
    return (
      <Card title={'Article: ' + this.state.article.title}>
        Category: <span className="label label-default">{this.state.article.category}</span><br/>
        Votes: {this.state.article.votes}<br/>
        <button className="btn btn-outline-success" onClick={(e: Event) => {
            articleService.upvoteArticle(this.state.article.id).then(res => {
                this.update();
            });
        }}>👍</button>
        <button className="btn btn-outline-danger" onClick={(e: Event) => {
            articleService.downvoteArticle(this.state.article.id).then(res => {
                this.update();
            });
        }}>👎</button>
        <hr/>
        <div>
          <div>
            <strong>{this.state.article.abstract}</strong>
          </div>
          <div>{this.state.article.text}</div>
        </div>
        <hr/>
        <Card title="Comments">
            <textarea rows="2" cols="50" ref={e => this.comment = e}></textarea><br/>
            <button className="btn btn-outline-primary" onClick={(e: Event) => {
                articleService.postCommentOnArticle(this.state.article.id, this.comment.value).then(res => {
                    this.update();
                });
            }}>Post comment</button>
            {this.state.article.Comments.map(comment => <div><hr/><p>{comment.text}</p></div>)}
        </Card>
      </Card>
    );
  }

  // Helper function to update component
  update() {
    articleService
      .getArticle(this.props.match.params.id)
      .then(article => {
        this.setState({article: article});
      })
      .catch((error: Error) => {
        Alert.danger('Error getting article ' + this.props.match.params.id + ': ' + error.message);
      });
  }

  componentDidMount() {
    this.update();
  }

  // Called when the this.props-object change while the component is mounted
  // For instance, when navigating from path /articles/1 to /articles/2
  componentWillReceiveProps() {
    setTimeout(() => {
      this.update();
    }, 0); // Enqueue this.update() after props has changed
  }
}

class NewArticle extends React.Component<{}> {
  form;
  title;
  abstract;
  text;
  category;

  onAdd: Signal<> = new Signal();

  render() {
    return (
      <Card title="New Article">
        <Form
          ref={e => (this.form = e)}
          submitLabel="Add Article"
          groups={[
            {label: 'Title', input: <input ref={e => (this.title = e)} type="text" required />},
            {label: 'Abstract', input: <textarea ref={e => (this.abstract = e)} rows="2" required />},
            {label: 'Text', input: <textarea ref={e => (this.text = e)} rows="3" required />},
            {label: 'Category', input: <input ref={e => (this.category = e)} type="text" required />},
            {checkInputs: [{label: 'I have read, understand and accept the terms and ...', input: <input type="checkbox" required />}]}
          ]}
        />
      </Card>
    );
  }

  componentDidMount() {
    if (this.form) {
      this.form.onSubmit.add(() => {
        if (!this.title || !this.abstract || !this.text || !this.category) return;
        articleService
          .addArticle(this.title.value, this.abstract.value, this.text.value, this.category.value)
          .then(id => {
            if (this.form) this.form.reset();
            this.onAdd.dispatch();
            history.push('/articles/' + id);
          })
          .catch((error: Error) => {
            Alert.danger('Error adding article: ' + error.message);
          });
      });
    }
  }
}

class Articles extends React.Component<{}> {
  table;

  render() {
      //<NewArticle ref={e => (this.newArticle = e)} />
    return (
      <div>
        <Card title="Articles">
          <Table ref={e => (this.table = e)} header={['Title', 'Abstract', "Category"]} />
        </Card>
      </div>
    );
  }

  // Helper function to update component
  update() {
    articleService
      .getArticles()
      .then(articles => {
        if (this.table) this.table.setRows(articles.map(article => ({id: article.id, cells: [article.title, article.abstract, article.category]})));
      })
      .catch((error: Error) => {
        Alert.danger('Error getting articles: ' + error.message);
      });
  }

  componentDidMount() {
    if (this.table) {
      this.table.onRowClick.add(rowId => {
        history.push('/articles/' + rowId);
      });
    }
    this.update();
  }
}

class TopArticles extends React.Component<
    {match: {params: {name: string}}},
    {currentCategory: ?string, distinctCategories: string[], articlesToShow: Article[]}
> {
    state = {currentCategory: null, distinctCategories: [], articlesToShow: []};
    table: Table;

    update() {
        let currentCategory: string = this.props.match.params.name;
        if (!this.props.match.params.name) {
            currentCategory = "";
        }
        this.setState({currentCategory: currentCategory});

        articleService.getArticles().then(articles => {
            let distinctCategories: string[] = [];
            let articlesToShow: Article[] = [];

            articles.forEach(article => {
                if (!distinctCategories.includes(article.category)) {
                    distinctCategories.push(article.category);
                }

                if (article.category == currentCategory || !currentCategory) {
                    articlesToShow.push(article);
                }
            });

            articlesToShow.sort((a, b) => b.votes - a.votes);
            if (this.table) {
                this.table.setRows(articlesToShow.map(article => ({id: article.id, cells: [article.title, article.abstract, article.votes]})));
            }

            this.setState({
                distinctCategories: distinctCategories,
                articlesToShow: articlesToShow
            });
        });
    }

    render() {
        return (
            <Card title={"Top articles" + (this.state.currentCategory ? " in category " + this.state.currentCategory : "")}>
                <select onChange={(e: Event) => {
                    history.push('/articles/top/' + e.target.value);
                    this.update();
                }}>
                    <option value="">All categories</option>
                    {this.state.distinctCategories.map(category =>
                        <option value={category} key={category}>{category}</option>
                    )}
                </select>
                <Table ref={e => (this.table = e)} header={['Title', 'Abstract', "Votes"]} />
            </Card>
        );
    }

    // Called when the this.props-object change while the component is mounted
    // For instance, when navigating from path /articles/1 to /articles/2
    componentWillReceiveProps() {
        setTimeout(() => {
            this.update();
        }, 0); // Enqueue this.update() after props has changed
    }

    componentDidMount() {
        if (this.table) {
            this.table.onRowClick.add(rowId => {
                history.push('/articles/' + rowId);
            });
        }
        this.update();
    }
}

let root = document.getElementById('root');
if (root) {
  ReactDOM.render(
    <HashRouter>
      <div>
        <Alert />
        <NavigationBar brand="Articlr" links={[
            {to: '/articles', text: 'Articles'},
            {to: "/articles/new", text: "Write new article"},
            {to: "/articles/top", text: "Top articles"},
        ]} />
        <Switch>
            <Route exact path="/" component={Home} />
            <Route exact path="/articles" component={Articles} />
            <Route exact path="/articles/top/:name" component={TopArticles} />
            <Route exact path="/articles/top" component={TopArticles} />
            <Route exact path="/articles/new" component={NewArticle} />
            <Route exact path="/articles/:id" component={ArticleDetails} />
        </Switch>
      </div>
    </HashRouter>,
    root
  );
}



var Nav = React.createClass({
    render: function () {
        return (
            <nav className="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
                <a className="navbar-brand" href="#">Navbar</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <a className="nav-link" href="#">Home <span className="sr-only">(current)</span></a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Link</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Disabled</a>
                        </li>

                        <ul className="nav-item active">
                            <li className="nav-item">
                                <a className="nav-link" href="#">sign up</a>
                            </li>

                            <li className="nav-item">
                                <a className="nav-link" href="#">sign in</a>
                            </li>

                        </ul>

                    </ul>

                </div>
            </nav>
        );
    }
})

var Main = React.createClass({
    render: function () {
        return (
            <div>
                <Nav/>
                <div className="container">

                    <div className="starter-template">
                        <h1>Bootstrap starter template</h1>
                        <h1>main page</h1>
                    </div>

                </div>

                <footer className="footer">
                    <div className="container">
                        <span className="text-muted">Place sticky footer content here.</span>
                    </div>
                </footer>
                {{/*js 영역*/}}
            </div>

        );
    }
})

var Signup = React.createClass({
    render: function () {
        return (
            <div>

                <Nav/>

                <div className="container">

                    <div className="starter-template">
                        <h1>Bootstrap starter template</h1>
                        <h1>sign up page</h1>
                    </div>

                </div>{{/*js*/}}




            </div>
        );
    }
})

var App = React.createClass({
    getInitialState : function(){
        return{
            route :window.location.hash.substr(1)
        }
    },
    componentDidMount:function () {
        window.addEventListener('hashchange',()=>{
            this.setState({
                route:window.location.hash.substr(1)
            });
        })
    },

    render: function () {
        var Child;
        switch (this.state.route){
            case 'main' : Child = Main;break;
            case 'ss' : Child =  Signup;break;
            default : Child= Main;
        }
        return (<Child/>);
    }

});

React.render(<App/>, document.body);
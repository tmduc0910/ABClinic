const fs = require('fs')
const bodyParser = require('body-parser')
const jsonServer = require('json-server')

const server = jsonServer.create()
const router = jsonServer.router('./db.json')
const userdb = JSON.parse(fs.readFileSync('./users.json', 'UTF-8'))

server.use(jsonServer.defaults())
server.use(bodyParser.urlencoded({extended: true}))
server.use(bodyParser.json())

//Check if the user exist in database
function isAuthenticated({username, password}) {
    return userdb.users.findIndex(user => user.username === username && user.password === password) !== -1 
}

server.post('/auth/login', (req, res) => {
    const {username, password} = req.body
    if (isAuthenticated({username, password}) === false) {
        const status = 401
        const message = 'Incorrect username or password'
        res.status(status).json({status, message})
        return
    }

    let user = userdb.users[userdb.users.findIndex(user => user.username === username && user.password === password)]
    res.status(201).json({ username: user.username, password: user.password, name: user.name, email: user.email, gender: user.gender, phone: user.phone });
})

server.use(router)
server.listen(3000, () => {
    console.log('Run Auth API Server')
})


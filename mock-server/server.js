const fs = require('fs')
const bodyParser = require('body-parser')
const jsonServer = require('json-server')

const server = jsonServer.create()
const router = jsonServer.router('./db.json')
const userdb = JSON.parse(fs.readFileSync('./users.json', 'UTF-8'))

server.use(jsonServer.defaults())
server.use(bodyParser.urlencoded({extended: true}))
server.use(bodyParser.json())

//Check if the user account is correct in database
function isAuthenticated({email, password}) {
    return userdb.users.findIndex(user => user.email === email && user.password === password) !== -1 
}

//Check if the user exist in database
function isExist(email) {
    return userdb.users.findIndex(user => user.email === email) !== -1
}

server.post('/auth/sign_in', (req, res) => {
    const {email, password} = req.body
    if (isAuthenticated({email, password}) === false) {
        const status = 401
        const message = 'Incorrect email or password'
        res.status(status).json({status, message})
        return
    }

    let user = userdb.users[userdb.users.findIndex(user => user.email === email && user.password === password)]
    res.status(200).json({ status: "Success", data: { id: user.id, uid: user.uid, email: user.email, password: user.password, name: user.name, gender: user.gender, birthday: user.birthday, phone: user.phone, joinDate: user.joinDate, createdDate: user.createdDate, address: user.address }});
})

server.post('/auth/get-salt', (req, res) => {
    const {email} = req.body
    if (isExist(email) === false) {
        const status = 401
        const message = 'Email has not been registered yet!'
        res.status(status).json({status, message})
        return
    }

    let user = userdb.users[userdb.users.findIndex(user => user.email === email)]
    res.status(200).json({ status: "Success", salt: user.salt});
})

server.use(router)
server.listen(3000, () => {
    console.log('Run Auth API Server')
})


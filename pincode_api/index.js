//1
var http = require('http');
	express = require('express');
	path = require('path');
 	MongoClient = require('mongodb').MongoClient,
	Server = require('mongodb').Server,
	Db = require('mongodb').Db,
	tunnel = require('tunnel-ssh');
	CollectionDriver = require('./collectionDriver').CollectionDriver;
  //BSON = require('mongodb').pure().BSON,
  assert = require('assert');


var config = {
        host: '50.174.18.42',
        username: 'go2door',
        dstPort: 4002,
        password:'1!Ramshyam'
    };

var app = express();
app.set('port',process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(express.bodyParser());

var mongoHost = '50.174.18.42'; //A
var mongoPort = 16017; 
var collectionDriver;
 
//var mongoClient = new MongoClient(new Server(mongoHost, mongoPort)); //B
var server = tunnel(config, function (error, result) {
var db = new Db("gotodoor", new Server("50.174.18.42", 16017,
 {auto_reconnect: false, poolSize: 4}), {w:0, native_parser: false});

db.open(function(err, db) {
  assert.equal(null, err);



    // Authenticate
    db.authenticate('mygo2door', '2015ghanshyam', function(err, result) {
      assert.equal(null, err);
      console.log("Authenticated to MongoDB");
	  collectionDriver = new CollectionDriver(db); //F
      //db.close();
    });
 });
});


app.use(express.static(path.join(__dirname,'public')));

app.get('/:collection', function(req, res, next) {  
   var params = req.params;
   var query = req.query.query; //1
   if (query) {
        query = JSON.parse(query); //2
        collectionDriver.query(req.params.collection, query, returnCollectionResults(req,res)); //3
   } else {
        collectionDriver.findAll(req.params.collection, returnCollectionResults(req,res)); //4
   }
});
 
function returnCollectionResults(req, res) {
    return function(error, objs) { //5
        if (error) { res.send(400, error); }
          else { 
                    if (req.accepts('html')) { //6
                        res.render('data',{objects: objs, collection: req.params.collection});
                    } else {
                        res.set('Content-Type','application/json');
                        res.send(200, objs);
                }
        }
    };
};
 
app.get('/:collection/:entity', function(req, res) { //I
   var params = req.params;
   var entity = params.entity;
   var collection = params.collection;
   if (entity) {
       collectionDriver.get(collection, entity, function(error, objs) { //J
          if (error) { res.send(400, error); }
          else { res.send(200, objs); } //K
       });
   } else {
      res.send(400, {error: 'bad url', url: req.url});
   }
});

app.use(function (req,res) { //1
    res.render('404', {url:req.url}); //2
});

//2 
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
 
console.log('Server running on port 3000.');

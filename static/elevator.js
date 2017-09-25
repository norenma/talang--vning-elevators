var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
var currentState;

var init = () => {
  ctx.beginPath();
  ctx.rect(20, 40, 50, 50);
  ctx.fill();
  ctx.closePath();
  
};

var render = () => {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
    renderButtons(ctx);
    ctx.fillStyle = "#99ccff";
    
  this.currentState.forEach((elevator, i) => {
    ctx.beginPath();
    ctx.rect(
      50 + i * 200,
      canvas.height - elevator.currentFloor * 50,
      50,
      50
    );
    ctx.fill();
    ctx.closePath();
  });
};

 var renderButtons = ctx => {
    ctx.fillStyle = "#000000"; 
    ctx.font = '24px serif';
    
     for(let i = 0; i < 20; i++) {
        ctx.fillText(i+1, 20, canvas.height - i*50 - 20);        
     }
 }

var getFromServer = () => {
  fetch("http://localhost:8080/rest/v1/snap")
    .then(data => data.json())
    .then(data => (this.currentState = data));
};

var requestFloor = e => {
    console.log(document.getElementById('floor').value)
    fetch("http://localhost:8080/rest/v1/request/" + document.getElementById('floor').value, 
    {
        method: "POST"
    }).then(data => console.log(data));
};

document.querySelector('#floor').addEventListener('keypress', (e) => {
  var key = e.which || e.keyCode;
  if (key === 13) { 
    requestFloor();
  }
});

setInterval(getFromServer, 500);
setInterval(render, 130);

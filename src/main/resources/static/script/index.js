const getResources = async() => {
    const response = await fetch('http://localhost:8080/resource')
    const json = await response.json()
    const table = document.getElementById('resources')
    table.innerHTML = ''
    for (const resource of json) {
        var html = "<tr><td>" + resource.name + "</td><td>" + resource.url + "</td><td>" +
                   resource.creationDate + "</td><td>" + resource.status + "</td></tr>"
        table.innerHTML = table.innerHTML + html
    }

    setTimeout(getResources, 5000)
}

document.addEventListener('DOMContentLoaded', function() {
   getResources()
}, false);

function addResource() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/resource", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({
        name: document.getElementById("name").value,
        url: document.getElementById("url").value
    }));
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status != 201) {
            var jsonResponse = JSON.parse(xhr.responseText);
                alert(jsonResponse['message']);
            }
        }
    }
}
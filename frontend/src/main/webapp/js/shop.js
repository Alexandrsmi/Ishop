function addProductToBucket(button, productId) {
    changeBucket(button, "removeProductFromBucket-" + productId, productId, "addProductToBucket");
}
function removeProductFromBucket(button, productId) {
    changeBucket(button, "addProductToBucket-" + productId, productId, "removeProductFromBucket");
}

function changeBucket(button, updateButtonId, productId, path) {
    button.style.display = "none";
    var progressBar = document.getElementById("changeBucketProgress-" + productId);
    progressBar.style.display = "";
    var url = "/" + path + "?productId=" + productId;
    var callback = function (response) {
        progressBar.style.display = "none";
        document.getElementById(updateButtonId).style.display = "";
        document.getElementById("bucket_size").innerHTML = response;
    };
    executeAJAX(url, callback);
}

function removeProductFromBucketTable(productId) {
    var buttons = document.getElementsByClassName("deleteProductButton");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }
    var url = "/removeProductFromBucket?productId=" + productId;
    var callback = function (response) {
        location.reload();
    };
    executeAJAX(url, callback);
}

function executeAJAX(url, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            callback(this.responseText);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function updateProductCountInBucket(productId) {
    var productCount = document.getElementById("count-" + productId).value;
    if (!isNormalInteger(productCount)) {
        alert("incorrect value for product count. Must be positive integer");
    } else {
        var url = "/updateProductCountInBucket?productId=" + productId + "&productCount=" + productCount;
        var callback = function (response) {
            var price = parseFloat(document.getElementById("price-" + productId).innerHTML);
            var productTotalPrice = productCount * price;
            document.getElementById("totalProductPrice-" + productId).innerHTML = productTotalPrice + "";
            var allTotalPrices = document.getElementsByClassName("totalProductPrice");
            var totalPrice = 0;
            for (var i = 0; i < allTotalPrices.length; i++) {
                totalPrice += parseFloat(allTotalPrices[i].innerHTML);
            }
            document.getElementById("totalPrice").innerHTML = totalPrice + "";
        };
        executeAJAX(url, callback);
    }
}
function isNormalInteger(str) {
    var n = Math.floor(Number(str));
    return String(n) === str && n > 0;
}

function releaseOrder(button) {
    button.disabled = true;
    var url = "/releaseOrder";
    var callback = function (response) {
        alert("Order is completed");
        location.reload();
    };
    executeAJAX(url, callback);
}

function sortProductsList() {
    var form = document.getElementById("viewProductsForm");
    form.submit();

}
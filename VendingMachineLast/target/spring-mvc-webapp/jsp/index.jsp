<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/home.css" rel="stylesheet">
    </head>

    <body>
        <div>
            <h1 id="homePageTitle">Vending Machine</h1>
        </div>
        <hr>

        <div class="container">
            <div class="row">

                <!-- below is the column for my items -->

                <div class="col-md-9" >   
                    <div class="row" id="itemSection">
                        <c:forEach var= "currentItem" items="${itemList}">
                            <div class = "col-md-4 item" >
                                <form action="selectItem" method="POST">
                                    <button type="submit" class="btn btn-default itemButton" name="itemButtons" value="${currentItem.id}" id="${currentItem.id}">
                                        <p class ="id"> <c:out value="${currentItem.id}"/></p>
                                        <h3><c:out value="${currentItem.item}"/></h3>
                                        <p><c:out value="${currentItem.price}"/></p>
                                        <p><c:out value= "Quantity Left:  ${currentItem.quantity}"/></p>
                                    </button>
                                </form>                               
                            </div>
                        </c:forEach>
                    </div>
                </div>


                <!-- below is the column for the options -->

                <div class="col-md-3">

                    <div class="row threeColumnSection" id="moneyInput">
                        <h3>Total $ Entered</h3>

                        <input class="form-control" style="text-align: center" value="${userMoney}" id="totalMoneyInput" placeholder="Enter Money" required readonly />

                        <div class="form-group">
                            <form action="addMoney" method="POST"><button type="submit" name ="moneyButton" class="btn btn-success col-md-6 moneyInputButtons" id="dollar" value="one">Add Dollar</button></form>
                            <form action="addMoney" method="POST"><button type="submit" name ="moneyButton" class="btn btn-success col-md-6 moneyInputButtons" id="quarter" value=".25">Add Quarter</button></form>
                        </div>

                        <div class="form-group">
                            <form action="addMoney" method="POST"><button type="submit" name ="moneyButton" class="btn btn-success col-md-6 moneyInputButtons" id="dime" value=".10">Add Dime</button></form>

                            <form action="addMoney" method="POST"><button type="submit" name ="moneyButton" class="btn btn-success col-md-6 moneyInputButtons" id="nickle" value=".05">Add Nickel</button></form>
                        </div>

                    </div>

                    <div class="row threeColumnSection" id="messageSection">
                        <div class="col-md-12" id=""><h3>Messages</h3></div>
                        <input class="form-control" style="text-align: center" value="${message}" id="messagesWindow" placeholder="" readonly/>
                        <div class="col-md-4" id=""><h4>Item: </h4></div>
                        <input class="form-control" style="text-align: center" value="${selectedItem}" id="itemDisplay" placeholder="" required readonly/>
                        <div><form action="makePurchase" method ="POST"><button type="submit" class="btn btn-primary btn-lg btn-block" id="makePurchaseButton" name="makePurchase" >Make Purchase</button></form></div>
                    </div>

                    <div class="row threeColumnSection" id="changeSection">
                        <div class="col-md-12" id=""><h3>Change</h3></div>
                        <input class="form-control" style="text-align: center" value="${changeToReturn}" id="changeDisplayWindow" placeholder="" required readonly/>
                        <div><form action="returnChange" method ="POST"><button type="submit" class="btn btn-primary btn-lg btn-block" id="returnChangeButton" name="returnChange">Return Change</button></form></div>
                    </div>

                </div>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>


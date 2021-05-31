<?php
$conn = new mysqli("localhost", "root", "", "android");

 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
$stmt = $conn->prepare("SELECT p.productId, p.libelleProduct, p.initialPrice, p.finalPrice,p.productDesc,p.imageUrl,a.startDate,a.EndDate, c.libelleCategorie FROM product p INNER JOIN auction a on p.auctionId = a.auctionId INNER JOIN categorie c on c.categorieId = p.categorieId");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($productId, $libelleProduct, $initialPrice, $finalPrice, $productDesc,$imageUrl,$startDate, $EndDate, $libelleCategorie );
 
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['productId'] = $productId; 
 $temp['libelleProduct'] = $libelleProduct; 
 $temp['initialPrice'] = $initialPrice; 
 $temp['finalPrice'] = $finalPrice;
 $temp['productDesc'] = $productDesc;  
 $temp['imageUrl'] = $imageUrl; 
 $temp['startDate'] = $startDate; 
 $temp['EndDate'] = $EndDate;  
 $temp['libelleCategorie'] = $libelleCategorie;
 array_push($products, $temp);
 }

 //displaying the result in json format 
 echo json_encode($products, JSON_UNESCAPED_SLASHES)
?>
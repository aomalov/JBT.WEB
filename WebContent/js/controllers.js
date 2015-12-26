/**
 * Created by Sandeep on 01/06/14.
 */
angular.module('testRest.controllers',[]).controller('LoginController',function($scope,$stateParams){
  console.log("logon screen");
}).controller('CustomerListController',function($scope,$state,popupService,$window,Customer){

//    $scope.customers=Customer.query(function() {
//        console.log($scope.customers);
//    });
	
	  Customer.query(function(result) {
		  $scope.customers=result;
      }, function(status,data) {
    	  console.log(data);
      });

    $scope.deleteCustomer=function(customer){
        if(popupService.showPopup('Really delete this?')){
            customer.$delete(function(){
            	//refresh the customer list
            	$scope.customers=Customer.query();
            });
        }
    }

}).controller('CustomerViewController',function($scope,$stateParams,Customer){

    $scope.customer=Customer.get({id:$stateParams.id});

}).controller('CustomerCreateController',function($scope,$state,$stateParams,Customer){

    $scope.customer=new Customer();

    $scope.addCustomer=function(){
        $scope.customer.$save(function(){
            $state.go('customers');
        });
    }

}).controller('CustomerEditController',function($scope,$state,$stateParams,Customer){

    $scope.updateCustomer=function(){
        $scope.customer.$update(function(){
            $state.go('customers');
        });
    };

    $scope.loadCustomer=function(){
        $scope.customer=Customer.get({id:$stateParams.id});
    };

    $scope.loadCustomer();
});
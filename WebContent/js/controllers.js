/**
 * Created by Sandeep on 01/06/14.
 */
angular.module('testRest.controllers',[]).controller('LoginController',function($scope,$stateParams,clientAuthTypeService){
  console.log("logon screen");
  if($stateParams.clientType) {
	  console.log($stateParams.clientType);
	  $scope.onlineUser=$stateParams.clientType;
	  clientAuthTypeService.setClientType($stateParams.clientType);
  }
  else { 
	  $scope.onlineUser="Guest";
	  clientAuthTypeService.setClientType("Guest");
  }
  
}).controller('NavbarMenuController',function($scope,clientAuthTypeService){
	
	  console.log("setting up nav bar");
	  
      console.log(clientAuthTypeService.clientType);
      
      $scope.clientType = 'Guest';
      
      $scope.$on('eventClientTypeChanged', function() {
          $scope.clientType = clientAuthTypeService.clientType;    
      });
	  
}).controller('restResponseController', ['$scope', 'restResponseService', function($scope, restResponseService) {
    
//    $scope.$watch(function () { return restResponseService.messageText; }, function() {
//      $scope.messageText = restResponseService.messageText;
//      $scope.messageType = restResponseService.messageType;
//    });
    
	$scope.$watch('messageText', function() {
       restResponseService.messageText = $scope.messageText; 
    });

    $scope.$on('eventRestResponse', function() {
      $scope.messageText = restResponseService.messageText;
      $scope.messageType = restResponseService.messageType;    
    });
    
}]).controller('CustomerListController',function($scope,$state,popupService,$window,Customer, restResponseService){

	  Customer.query(function(result) {
		  $scope.customers=result;
      }, function(response) {
    	  console.log(response.data);
    	  restResponseService.applyAlert(response.data,$scope);
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

}).controller('CustomerCreateController',function($scope,$state,$stateParams,Customer,restResponseService){

    $scope.customer=new Customer();

    $scope.addCustomer=function(){
        $scope.customer.$save(function(){
            $state.go('customers');
        },function(response){
        	console.log(response.data);
        	restResponseService.applyAlert(response.data,$scope);
        });
    }

}).controller('CustomerEditController',function($scope,$state,$stateParams,Customer,restResponseService){

    $scope.updateCustomer=function(){
        $scope.customer.$update(function(){
            $state.go('customers');
        },function(response){
        	console.log(response.data);
        	restResponseService.applyAlert(response.data,$scope);
        });
    };

    $scope.loadCustomer=function(){
        $scope.customer=Customer.get({id:$stateParams.id});
    };

    $scope.loadCustomer();
});
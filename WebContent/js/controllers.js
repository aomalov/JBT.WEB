/**
 * Created by Sandeep on 01/06/14.
 */
angular.module('testRest.controllers',[]).controller('LoginController',function($scope,$stateParams){
  console.log("logon screen");
  
}).controller('restResponseController', ['$scope', 'restResponseService', function($scope, restResponseService) {
    
    $scope.$watch(function () { return restResponseService.messageText; }, function() {
      $scope.messageText = restResponseService.messageText;
      $scope.messageType = restResponseService.messageType;
    });
    
    $scope.$watch('messageText', function() {
       restResponseService.messageText = $scope.messageText; 
    });

    // $scope.$on('eventRestResponse', function() {
    //   $scope.messageText = restResponseService.messageText;
    //   $scope.messageType = restResponseService.messageType;    
    //  });
    
}]).controller('CustomerListController',function($scope,$state,popupService,$window,Customer, restResponseService){

	  Customer.query(function(result) {
		  $scope.customers=result;
      }, function(response) {
    	  console.log(response.data);
    	  restResponseService.applyAlert(response.data);
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
        	restResponseService.applyAlert(response.data);
        });
    }

}).controller('CustomerEditController',function($scope,$state,$stateParams,Customer,restResponseService){

    $scope.updateCustomer=function(){
        $scope.customer.$update(function(){
            $state.go('customers');
        },function(response){
        	console.log(response.data);
        	restResponseService.applyAlert(response.data);
        });
    };

    $scope.loadCustomer=function(){
        $scope.customer=Customer.get({id:$stateParams.id});
    };

    $scope.loadCustomer();
});
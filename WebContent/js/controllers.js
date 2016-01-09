/**
 * Inspired by Sandeep on 01/06/14.
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
}).controller('NavbarMenuController',function($scope,$cookies,clientAuthTypeService){
	
	  console.log("setting up nav bar");
	  
      console.log(clientAuthTypeService.clientType);
      
      $scope.clientType = ($cookies.get('clientType')) ? $cookies.get('clientType') :'Guest';
      
      $scope.$on('eventClientTypeChanged', function() {
          $scope.clientType = clientAuthTypeService.clientType;    
      });
	  
}).controller('restResponseController', ['$scope', 'restResponseService', function($scope, restResponseService) {
    
	$scope.$watch('messageText', function() {
       restResponseService.messageText = $scope.messageText; 
    });

    $scope.$on('eventRestResponse', function() {
      $scope.messageText = restResponseService.messageText;
      $scope.messageType = restResponseService.messageType;    
    });
    
}])

// ADMIN - CUSTOMERS ================================================================
.controller('CustomerListController',function($scope,$state,Customer, restResponseService, modalConfirmationService){

	  Customer.query(function(result) {
		  $scope.customers=result;
      }, function(response) {
    	  console.log(response.data);
    	  restResponseService.applyAlert(response.data,$scope);
      });

      $scope.deleteCustomer=function(customer){
    	var modalInstance = modalConfirmationService.showDialog(customer.cust_NAME);
    		
    	modalInstance.result.then(function () {
    		  console.log("to deletion");
    	      customer.$delete(function(){
              	//refresh the customer list
              	$scope.customers=Customer.query();
              });
    	    });	
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
    
})

// COMPANY-COUPONS ================================================================
.controller('CompanyCouponListController',function($scope,$state,CompanyCoupon, restResponseService, modalConfirmationService){

	CompanyCoupon.query(function(result) {
		  $scope.coupons=result;
    }, function(response) {
  	  console.log(response.data);
  	  restResponseService.applyAlert(response.data,$scope);
    });

    $scope.deleteCoupon=function(coupon){
  	var modalInstance = modalConfirmationService.showDialog(coupon.title);
  		
  	modalInstance.result.then(function () {
  		  console.log("to deletion");
  	      coupon.$delete(function(){
            	//refresh the coupons list
            	$scope.coupons=CompanyCoupon.query();
            });
  	    });	
  }

}).controller('CompanyCouponViewController',function($scope,$stateParams,CompanyCoupon){

  $scope.coupon=CompanyCoupon.get({id:$stateParams.id});

}).controller('CouponCreateController',function($scope,$state,$stateParams,CompanyCoupon,restResponseService){

  $scope.coupon=new CompanyCoupon();

  $scope.addCoupon=function(){
	  console.log($scope.coupon);
      $scope.coupon.$save(function(){
          $state.go('company-coupons');
      },function(response){
      	console.log(response.data);
      	restResponseService.applyAlert(response.data,$scope);
      });
  }

}).controller('CouponEditController',function($scope,$state,$stateParams,CompanyCoupon,restResponseService){

  $scope.updateCoupon=function(){
	  console.log($scope.coupon);
      $scope.coupon.$update(function(){
          $state.go('company-coupons');
      },function(response){
      	console.log(response.data);
      	restResponseService.applyAlert(response.data,$scope);
      });
  };

  $scope.loadCoupon=function(){
      $scope.coupon=CompanyCoupon.get({id:$stateParams.id});
  };

  $scope.loadCoupon();
  
}).controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, toDelete) {

	  $scope.toDelete = toDelete;
	
	  $scope.ok = function () {
	    $uibModalInstance.close("ok");
	  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	  
});
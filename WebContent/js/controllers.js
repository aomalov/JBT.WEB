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
    	var modalInstance = modalConfirmationService.showDialog("Do you really want to delete "+customer.cust_NAME);
    		
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

}).controller('CouponEditController',function($scope,$state,$stateParams,$log,CompanyCoupon,restResponseService,uiUploader){

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
  
  $scope.btn_uploadRemove = function() {
      $log.info('deleting files');
      uiUploader.removeAll();
      $scope.files=[];
  };

  $scope.btn_doUpload = function() {
      $log.info('uploading...');
      uiUploader.startUpload({
          url: 'coupon.web/rest/company/coupons/'+$scope.coupon.id+'/imgupload',
//          concurrency: 2,
//          onProgress: function(file) {
//              $log.info(file.name + '=' + file.humanSize);
//              $scope.$apply();
//          },
          onCompleted: function(file, response) {
              $log.info(file + 'response' + response);
          }
      });
  };
  
  $scope.files = [];
  $scope.$on("fileSelected", function (event, args) {
	    $scope.$apply(function () {            
	        //add the file object to the scope's files collection
	        $scope.files.push(args.file);
	        uiUploader.addFiles($scope.files);
	    });
  });

  $scope.loadCoupon();
  
})

// CUSTOMER-COUPONS ================================================================
.controller('CustomerCouponListController',function($scope,$state,CustomerCoupon, restResponseService, modalConfirmationService){

	CustomerCoupon.query(function(result) {
		  $scope.coupons=result;
    }, function(response) {
  	  console.log(response.data);
  	  restResponseService.applyAlert(response.data,$scope);
    });

}).controller('CustomerCouponViewController',function($scope,$stateParams,CustomerCoupon){

  $scope.coupon=CustomerCoupon.get({id:$stateParams.id});

}).controller('PurchaseCouponController',function($scope,$state,$stateParams,CustomerCoupon,restResponseService,modalConfirmationService){

  CustomerCoupon.showavail(function(result) {
	      $scope.coupons=result;
	      $scope.totalItems = $scope.coupons.length;
          $scope.itemsPerPage = 3;
          $scope.currentPage = 1;
  }, function(response) {
	  console.log(response.data);
	  restResponseService.applyAlert(response.data,$scope);
  });
  
  $scope.getCoupons = function() {
  	if($scope.totalItems>0)
        return $scope.coupons.slice(($scope.currentPage-1)*$scope.itemsPerPage,$scope.currentPage*$scope.itemsPerPage);
  };
  
  $scope.purchaseCoupon=function(coupon){
	  	var modalInstance = modalConfirmationService.showDialog("Do you really want to purchase "+coupon.title);
	  		
	  	modalInstance.result.then(function () {
	  		  console.log("to purchase");
	  	      coupon.$purchase(function(){
	            	//refresh the coupons list
	            	$scope.coupons=CustomerCoupon.query();
	            });
	  	    });	
  }

})
// GENERAL PURPOSE controllers
.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, theQuestion) {

	  $scope.theQuestion = theQuestion;
	
	  $scope.ok = function () {
	    $uibModalInstance.close("ok");
	  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	  
});
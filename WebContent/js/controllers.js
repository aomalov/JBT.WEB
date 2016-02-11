/**
 * Inspired by Sandeep on 01/06/14.
 */
angular.module('testRest.controllers',[]).controller('LoginController',function($scope,$stateParams,$http,clientAuthTypeService,restResponseService){
	
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
  
  $scope.doLogin = function() {
	  
	  $http.post("coupon.web/rest/login",$scope.login)
	    .then(function(result) {
	    	window.location.replace(result.data.redirectUrl);
	      }, function(response) {
	    	  console.log(response.data);
	    	  restResponseService.applyAlert(response.data,$scope);
	      });
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
//ADMIN - COMPANIES
.controller('CompanyListController',function($scope,$state,Company, restResponseService, modalConfirmationService){

	  Company.query(function(result) {
		  $scope.companies=result;
      }, function(response) {
    	  console.log(response.data);
    	  restResponseService.applyAlert(response.data,$scope);
      });

      $scope.deleteCompany=function(company){
    	var modalInstance = modalConfirmationService.showDialog("Do you really want to delete "+company.comp_NAME);
    		
    	modalInstance.result.then(function () {
    		  console.log("to deletion");
    	      company.$delete(function(){
              	//refresh the company list
              	$scope.companies=Company.query();
              });
    	    });	
    }

}).controller('CompanyViewController',function($scope,$stateParams,Company){

    $scope.company=Company.get({id:$stateParams.id});

}).controller('CompanyCreateController',function($scope,$state,$stateParams,Company,restResponseService){

    $scope.company=new Company();

    $scope.addCompany=function(){
        $scope.company.$save(function(){
            $state.go('companies');
        },function(response){
        	console.log(response.data);
        	restResponseService.applyAlert(response.data,$scope);
        });
    }

}).controller('CompanyEditController',function($scope,$state,$stateParams,Company,restResponseService){

    $scope.updateCompany=function(){
        $scope.company.$update(function(){
            $state.go('companies');
        },function(response){
        	console.log(response.data);
        	restResponseService.applyAlert(response.data,$scope);
        });
    };

    $scope.loadCompany=function(){
        $scope.company=Company.get({id:$stateParams.id});
    };

    $scope.loadCompany();
    
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

}).controller('CouponEditController',function($scope,$state,$stateParams,$log,CompanyCoupon,restResponseService){

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
  
  $scope.files = [];
  $scope.$on("fileSelected", function (event, args) {
	    $scope.$apply(function () {            
	        //add the file object to the scope's files collection
	        $scope.files.push(args.file);
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

}).controller('PurchaseCouponController',function($scope,$http,$state,$stateParams,CustomerCoupon,restResponseService,modalConfirmationService){

  CustomerCoupon.showavail(function(result) {
	      $scope.coupons=result;
	      $scope.totalItems = $scope.coupons.length;
          $scope.itemsPerPage = 3;
          $scope.currentPage = 1;
  }, function(response) {
	  console.log(response.data);
	  restResponseService.applyAlert(response.data,$scope);
  });
  
  $scope.lookupCoupons = function(val) {
	    return $http.get('coupon.web/rest/customer/coupons', {
	      params: {
	        owned: 'no',
	        pattern: val
	      }
	    }).then(function(response){
	      return response.data.map(function(item){
	        return item;
	      });
	    });
	  };
  
  $scope.quickBuyCoupon = function(couponId) {
	  CustomerCoupon.get({id:couponId},function(result) {
		  $scope.purchaseCoupon(result);
	  });
  }	  
	  
  $scope.getCoupons = function() {
  	if($scope.totalItems>0)
        return $scope.coupons.slice(($scope.currentPage-1)*$scope.itemsPerPage,$scope.currentPage*$scope.itemsPerPage);
  };
  
  $scope.purchaseCoupon=function(coupon){
	  	var modalInstance = modalConfirmationService.showDialog("Do you really want to purchase "+coupon.title);
	  		
	  	modalInstance.result.then(function () {
	  		  console.log("to purchase");
	  	      coupon.$purchase(function(result){
	            	//refresh the coupons list
	  	    	restResponseService.applyAlert({messageText:'Coupon purchased',messageType:'success'},$scope);
	  	    	  $state.go('customer-coupons');
	            }, function(response) {
	          	  console.log(response.data);
	        	  restResponseService.applyAlert(response.data,$scope);
	          });
	  	    });	
  }

})
//REPORTING controllers
.controller('InvoicesSimpleReportController',function($scope,$state, $http, restResponseService){

	$scope.adminMode = ($state.current.data) ? $state.current.data.adminMode : false ;
	
	$scope.timestampConvert = function(ts) {
		return moment(ts).format("DD-MM-YYYY HH:mm:ss");
	};
	
    $http.get('coupon.web/rest'+$state.current.url)
      .then(function(response){
    	      $scope.reportInvoices = response.data;
            },function(errResponse) {
          	  console.log(errResponse.data);
        	  restResponseService.applyAlert(errResponse.data,$scope);
            });

}).controller('InvoicesByPartyReportController',function($scope,$state, $http, Customer, Company, restResponseService){

	$scope.adminMode = ($state.current.data) ? $state.current.data.adminMode : false ;
	
	$scope.timestampConvert = function(ts) {
		return moment(ts).format("DD-MM-YYYY HH:mm:ss");
	};

	if($state.current.data.party=="customer")
		Customer.query(function(result) {
			  $scope.customers=result;
	     }, function(response) {
	   	  console.log(response.data);
	   	  restResponseService.applyAlert(response.data,$scope);
	     });
	else if($state.current.data.party=="company")
		Company.query(function(result) {
			  $scope.companies=result;
	     }, function(response) {
	   	  console.log(response.data);
	   	  restResponseService.applyAlert(response.data,$scope);
	     });
	
	$scope.runReport = function() {
	    $http.get('coupon.web/rest'+$state.current.url+'/'+ $scope.selectedId )
	      .then(function(response){
	    	      $scope.reportInvoices = response.data;
	            },function(errResponse) {
	          	  console.log(errResponse.data);
	        	  restResponseService.applyAlert(errResponse.data,$scope);
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
/**
 * Inspired by Sandeep on 01/06/14.
 */

angular.module('testRest.services',[])
// FACTORIES - REST RESOURCES
.factory('Customer',function($resource){
    return $resource('coupon.web/rest/customers/:id',{id:'@id'},{
        update: {
            method: 'PUT' , params:{id:''}
        }
    });
}).factory('CustomerCoupon',function($resource){
    return $resource('coupon.web/rest/customer/coupons/:id',{id:'@id'}, {
        purchase:  { method: 'PUT' , params:{id:''} },
        showavail: { method: 'GET',  params:{id:'',owned:'no'} , isArray:true}
        }
    );
}).factory('CompanyCoupon',function($resource){
    return $resource('coupon.web/rest/company/coupons/:id',{id:'@id'},{
        update: {
            method: 'PUT' , params:{id:''}
        }
    });
// SERVICES
}).service('restResponseService', function() {
	   
    this.messageText = "";
    this.messageType = "success";
    
    this.applyAlert = function(restJsonResponse,localScope) {
        this.messageText = restJsonResponse.messageText;
        this.messageType = restJsonResponse.messageType;
        localScope.$emit("eventRestResponse");
        if(restJsonResponse.redirectUrl)
        	window.location.replace(restJsonResponse.redirectUrl);
    };
    
}).service('clientAuthTypeService',function($rootScope,$cookies){
	
    this.clientType = "Guest";
    
    this.setClientType = function(clientType) {
    	this.clientType = clientType;
    	$cookies.put('clientType',clientType);
    	$rootScope.$broadcast("eventClientTypeChanged");
    };
}).service('modalConfirmationService',function($uibModal){
	
	this.showDialog = function (modalQuestion) {
		return $uibModal.open({
		      animation: true,
		      templateUrl: 'coupon.web/partials/modal-confirmation.html',
		      controller: 'ModalInstanceCtrl',
		      size: 'sm',
		      resolve: {
		        theQuestion: function () {
		            return modalQuestion;
		        }
		      }
		    });
	}
})
// DIRECTIVES
.directive('fileUpload', function () {
 return {
    scope: true,        //create a new scope
    link: function (scope, el, attrs) {
        el.bind('change', function (event) {
            var files = event.target.files;
            //iterate files since 'multiple' may be specified on the element
            for (var i = 0;i<files.length;i++) {
                //emit event upward
                scope.$emit("fileSelected", { file: files[i] });
            }                                       
        });
    }
 };
});
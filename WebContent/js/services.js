/**
 * Created by Sandeep on 01/06/14.
 */

angular.module('testRest.services',[]).factory('Customer',function($resource){
    return $resource('coupon.web/rest/customers/:id',{id:'@id'},{
        update: {
            method: 'PUT' , params:{id:''}
        }
    });
}).factory('CompanyCoupon',function($resource){
    return $resource('coupon.web/rest/company/coupons/:id',{id:'@id'},{
        update: {
            method: 'PUT' , params:{id:''}
        }
    });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
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
    
}).service('clientAuthTypeService',function($rootScope){
	
    this.clientType = "Guest";
    
    this.setClientType = function(clientType) {
    	this.clientType = clientType;
    	$rootScope.$broadcast("eventClientTypeChanged");
    };
});
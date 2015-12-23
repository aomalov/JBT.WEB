/**
 * Created by Sandeep on 01/06/14.
 */

angular.module('testRest.services',[]).factory('Customer',function($resource){
    return $resource('coupon.web/rest/customers/:id',{id:'@id'},{
        update: {
            method: 'PUT'
        }
    });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});
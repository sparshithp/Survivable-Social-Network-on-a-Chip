/**
 * Created by feng on 11/5/14.
 */

module.exports = {
    citizen: ['join_community', 'share_status','exchange_information','search_information','locate_people','make_call'],
    coordinator: ['post_announcement','analyze_network'],
    monitor: ['measure_performance','measure_memory','trace_traffic','get_trace'],
    administrator: ['admin_profile','admin_content'],
    isHasTheAuthorization: function(useCaseName, privilegeLevel){

        if(privilegeLevel === 'citizen' ){
            return this.isContain(privilegeLevel, this.citizen);
        }
        if(privilegeLevel === 'coordinator' ){
            return (this.isContain(privilegeLevel, this.citizen) || this.isContain(privilegeLevel, this.coordinator));
        }
        if(privilegeLevel === 'monitor' ){
            return (this.isContain(privilegeLevel, this.citizen) || this.isContain(privilegeLevel, this.coordinator) || this.isContain(privilegeLevel, this.monitor));
        }
        if(privilegeLevel === 'administrator' ){
            return (this.isContain(privilegeLevel, this.citizen) || this.isContain(privilegeLevel, this.coordinator) || this.isContain(privilegeLevel, this.monitor) || this.isContain(privilegeLevel, this.administrator));
        }

    },
    isContain: function(privilegeLevel, array){

        for(var i=0;i<array.length;i++){
            if(array[i] == privilegeLevel){
                return true;
            }
        }
        return false;
    }

//    }

}
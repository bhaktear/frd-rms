$(document).ready(function(){
    get_loading();
    var type = getParameterByName("type");
    var tbl = "#user_upload_tbl";
    var page_header = "";
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");
    var pid = getParameterByName("id");
    var date = getParameterByName("date");
    $('#row_report_date').hide();
    
    function get_report_url(type,date){
        switch(type){
            case '1':
            default:
                var url = "/report?date=" + date;
                $('#row_report_date').show();
                break;
            case '2':
                var exchangeCode = getParameterByName("exchangeCode");
                var url = "/fileReport?exchangeCode=" + exchangeCode + "&id=" + pid;
                break;
            case '3':
                var url = "/errorReport?id=" + pid;
                page_header = "Error Data Report";
                break;
            case '4':
                var url = "/getErrorUpdateReport";
                page_header = "Error Data Report Update for Admin";
                break;
        }
        return {'url': url, 'page_header': page_header};
    }

    var url = "/getReportColumn?type=" + type;
    user_upload_report_ui(url,type,date);
    $(document).off('change','#report_date');
    $(document).on('change','#report_date', function(e){
        e.preventDefault();
        var val = $(this).val();
        user_upload_report_ui(url,type,val);
    });

    function user_upload_report_ui(url,type,date){
        var rdata = get_report_url(type,date);
        if(rdata.page_header)  $(".page-header").html(rdata.page_header);
        var report_url = rdata.url;
        var params = {'tbl': tbl,'url': report_url};
        get_ajax(url,"",get_user_upload_report,fail_func,"get","json",params);
    }
    

    function get_user_upload_report(resp,params){
        var sfun = [upload_report_ui];
        get_dynamic_dataTable(params.tbl, params.url, resp.column, sfun);        
    }

    function upload_report_ui(resp){
        edit_error_data(tbl);
        delete_error(tbl,csrf_token,csrf_header);
    }

    $(document).off('click',".view_exchange");
    $(document).on('click',".view_exchange",function(e){
        e.preventDefault();
        var id = $(this).attr("id");
        var exCode = $("#exCode_"+ id).val();
        var val = $(exCode).val();
        var base_url = $("#base_url").val();
        var url = base_url + "?type=2&exchangeCode=" + exCode + "&id=" + id;
        //var url = "/user-home-page?type=2&exchangeCode=" + exCode + "&id=" + id;
        window.location.href = url;
    });

    $(document).off('click',".view_error");
    $(document).on('click',".view_error",function(e){
        e.preventDefault();
        var id = $(this).attr("id");
        var params = { tdiv: '.modal-body'};
        var mparams = { 'modalID': 'myModal', 'modal_wrap':'#modal_wrap','modal_class':'modal-md', 'modal_title': 'View Error Data' };
        var url = "/error/viewError/" + id;
        gen_modal(url,params,mparams);
    });

    $(document).on("submit","#approve_error_form",function(e){
        e.preventDefault();
        var id = $("#id").val();
        var data = {'id':id, '_csrf': csrf_token, '_csrf_header': csrf_header};
        var url  = "/error/approve";
        var params = {'reload': true, 'tbl': tbl, 'modal_hide': 'true', 'modalID': 'myModal' };
        get_ajax(url,data,success_modal,fail_func,"post","json",params);
    });

});
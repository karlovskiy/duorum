/**
 * @author karlovsky
 * @since 1.0, 07/01/14
 */

(function (duorum) {
    var modules = {};

    duorum.addModule = function (name, moduleDeclaration) {
        modules[name] = moduleDeclaration;
    };

    duorum.initModules = function (names) {
        var moduleNames = names.split(/\s*,\s*/);
        for (var i = 0; i < moduleNames.length; i++) {
            var module = modules[moduleNames[i]];
            if (module) {
                module.call(this)
            }
        }
    };

}(window.duorum = window.duorum || {}));

duorum.addModule("users", function () {
    var auth = $("#authorities");
    if (auth.length != 0) {
        var authorities = auth.val().split(/\s*,\s*/);
        for (var i = 0; i < authorities.length; i++) {
            var authority = authorities[i];
            if (authority != '') {
                $("input#" + authority).prop("checked", true);
            }
        }
//        var all = [];

//        $("label.authority > span").each(function () {
//            var authority = $.trim($(this).html());
//            if (authority != "ROOT") {
//                var value = authority.split("_")[0];
//                if ($.inArray(value, all) == -1) {
//                    all.push(value);
//                }
//            }
//        });

        $("#form").submit(function (e) {
            var authorities = [];
            var form = $(this);
            form.find("label.authority").each(function () {
                var label = $(this);
                var authority = label.find("input");
                if (authority.is(':checked')) {
                    authorities.push(authority.attr("id"));
                }
            });
            form.find("#authorities").val(authorities.join(","));
        });

//        $("label.authority > input[type='checkbox']").change(function (e) {
//            var jthis = $(this);
//            var id = jthis.attr("id");
//
//            if (id == "ROOT") {
//                $("label.authority > input[type='checkbox'][id!='ROOT']").prop("checked", jthis.is(":checked"));
//            } else {
//                for (var i = 0; i < all.length; i++) {
//                    var mode = all[i];
//                    var edit = mode + "_EDIT";
//                    var view = mode + "_VIEW";
//                    var manager = mode + "_MANAGER";
//                    var editElement = $("label.authority > input[type='checkbox'][id='" + edit + "']");
//                    var viewElement = $("label.authority > input[type='checkbox'][id='" + view + "']");
//                    var managerElement = $("label.authority > input[type='checkbox'][id='" + manager + "']");
//                    if (id == edit && jthis.is(":checked")) {
//                        viewElement.prop("checked", true);
//                        if (managerElement.length != 0) {
//                            managerElement.prop("checked", true);
//                        }
//                    } else if (id == view && !jthis.is(":checked")) {
//                        editElement.prop("checked", false);
//                        if (managerElement.length != 0) {
//                            managerElement.prop("checked", false);
//                        }
//                    } else if (id == manager && jthis.is(":checked")) {
//                        viewElement.prop("checked", true);
//
//                    } else if (id == manager && !jthis.is(":checked")) {
//                        editElement.prop("checked", false);
//                    }
//                }
//            }
//        });
    }
});
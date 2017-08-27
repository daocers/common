/**
 * Created by daocers on 2017/8/26.
 */

/**
 * 在顶部标签栏打开新的标签,并激活当前标签
 * @param url 需要打开的页面的url，需要和菜单中配置的一样
 */
function openTab(url){
    parent.$("a.J_menuItem[href='" + url + "']", parent.document).trigger("click");
}


/**
 * 让指定的选项卡设置为有效
 * @param url
 */
function locateTab(url) {
    parent.$(".page-tabs-content > .J_menuTab", parent.document).removeClass("active");
    parent.$(".page-tabs-content > .J_menuTab[data-id='" + url + "']", parent.document).addClass("active");
}


/**
 * 删除选项卡 如果删除的是当前选项卡，直接设置第一个为active
 * @param url
 */
function removeTab(url) {
    console.log("删除", ".J_menuTab[data-id='" + url + "']");
    parent.$(".J_menuTab[data-id='" + url + "']", parent.document).remove();
    var tabs = parent.$(".J_menuTab", parent.document);
    if(tabs.length == 0){
        parent.$(tabs[0]).addClass("active");
    }
}


/**
 * 删除选项卡 删除当前选项卡，直接设置第一个为active
 * @param url
 */
function removeCurrentTab() {
    console.log("删除当前选项卡");
    var curTab = parent.$(".J_menuTab.active", parent.document);
    curTab.remove();

    var idx = parent.$(".J_menuTab", parent.document).index(curTab);
    if(idx > 1){
        parent.$(".J_menuTab:eq(" + (idx -1) + ")", parent.document).addClass("active");
    }
}

/**
 * 在当前选项卡中打开页面
 * 更改标签名称，打开标签后激活当前标签
 * @param url
 */
function openTabOnCurrent(url) {
    console.log("在当前页面上打开新标签")
    removeCurrentTab();
    openTab(url)
}
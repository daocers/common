<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <title>用户注册丨布谷培训</title>
    <meta name="robots" content="all">
    <!--<link href="https://js.51job.com/in/css/2017/public/base.css" rel="stylesheet" type="text/css" />-->
    <!--<link href="https://js.51job.com/in/css/2017/public/form.css" rel="stylesheet" type="text/css" />-->
    <!--<link href="https://js.51job.com/in/css/2017/member/login-register.css" rel="stylesheet" type="text/css" />-->
    <link href="../../assets/css/login/base.css" rel="stylesheet" type="text/css"/>
    <link href="../../assets/css/login/form.css" rel="stylesheet" type="text/css"/>
    <link href="../../assets/css/login/login-register.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        #rememberMe:focus{
            border-color: dodgerblue;
        }
    </style>
    <script language="javascript">
        var _tkd = _tkd || []; //点击量统计用
        var lang = [];
        var supporthttps = 1; //浏览器是否支持https
        var currenthttps = (window.location.protocol === 'https:') ? '1' : '0'; //当前是否为https
        window.cfg = {
            domain: {
                my: 'http://my.51job.com',
                login: (currenthttps === '1') ? 'https://login.51job.com' : 'http://login.51job.com',
                search: 'http://search.51job.com'
            }
        };

        window.cfg.url = {
            root: 'http://search.51job.com/jobsearch',
            image: 'http://img02.51jobcdn.com/im/2009',
            image_search: 'http://img05.51jobcdn.com/im/2009/search'
        }
        window.cfg.root_img = 'http://img03.51jobcdn.com/im/2016';
        window.cfg.root_www = 'http://www.51job.com';
        window.cfg.nowdate = '2017-03-10';
    </script>


    <script type="text/javascript" src="../../assets/js/jquery-2.2.0.min.js"></script>


    <!--<script language="javascript" src="https://js.51job.com/in/js/2016/jquery.js"></script>-->
    <!--<script language="javascript" src="https://js.51job.com/in/js/2016/login/jquery.placeholder.min.js"></script>-->
    <!--<script language="javascript" src="https://js.51job.com/in/js/2016/login/login.js"></script>-->
    <!--<script language="javascript" src="https://js.51job.com/in/js/2016/login/auth.js"></script>-->
    <!--<script language="javascript" src="https://js.51job.com/in/js/2016/login/register_c.js"></script>-->
</head>
<body>
<div class="pro">
    <div class="msg"><em id="warning"></em>最近有不法分子冒充布谷科技，让用户提供账户和密码。在此声明，我们绝不会通过电话、邮件或短信等形式询问用户名和密码。请用户提高警惕，增强自我保护意识。
    </div>
</div>
<div class="header">
    <div class="nag">
        <div class="in">
            <a href="/index.do"><img class="logo" width="40" height="40" src="../../assets/img/login/logo.png" alt="布谷培训"></a>
            <img class="slogen" width="180" height="25" src="../../assets/img/login/slogen.png" alt="在线考试就到布谷培训">

            <span class="gp" style="display:"></span>
            <span class="tl" style="display:">用户注册</span>
            <p class="nlink n2">
                <a href="/index.do">首页</a>
                <a href="/help/index.do" target="_blank">帮助中心</a>
            </p>
        </div>
    </div>
</div>
<div class="lrcon Fm">
    <div class="lrbox clearfix">
        <div class="lr_lf">
            <div class="lr_c">
                <label class="l5"></label>
                <div class="lr_cn">
                    <p>便捷的数据管理</p>
                    <div class="c_999 f12">使用office软件 兼容日常办公习惯</div>
                </div>
            </div>
            <div class="lr_c">
                <label class="l6"></label>
                <div class="lr_cn">
                    <p>丰富的试题策略</p>
                    <div class="c_999 f12">多种试题试卷策略 助您轻松出卷</div>
                </div>
            </div>
            <div class="lr_c">
                <label class="l7"></label>
                <div class="lr_cn">
                    <p>实时的状态监控</p>
                    <div class="c_999 f12">系统运行数据 实时输出</div>
                </div>
            </div>
            <div class="lr_c">
                <label class="l8"></label>
                <div class="lr_cn">
                    <p>个性的管理系统</p>
                    <div class="c_999 f12">按需定制系统参数 便捷管理</div>
                </div>
            </div>
        </div>

        <div class="lr_rt">
            <input type="hidden" id="lang" name="lang" value="c"/>
            <input type="hidden" id="scode" name="scode" value=""/>
            <div class="lr_h">用 户 注 册</div>
            <div class="errbox" style="display:none"><span class="err" id="account_err">帐号或密码错误</span><span
                    style="display:none" id="seekpwd">
                     ，是否 &nbsp;<a
                    href="https://login.51job.com/forgetpwd.php?lang=c&from_domain=i&source=&url=http%3A%2F%2Fwww.51job.com%2F">找回密码</a></span>
            </div>


            <form name="register" id="register">
                <input type="hidden" id="from_domain" name="from_domain" value="i"/>
                <div class="lr_e">
                    <label><span class="err" style="display:none" id="name_err">请输入布谷科技帐号</span>姓名</label>
                    <div class="txt" style="z-index:9999;position:relative;">
                        <input tabindex="1" class="ef" maxlength=100 id="name" name="name" type="text" autocomplete='off'
                               placeholder="姓名" value="${name}">
                        <%--onkeyup="show_emaillist(this, event)"--%>
                        <%--onkeydown="enterClickGetValue(event);--%>
                        <div class="ul" style="display:none;top:40px" id="name">
                        </div>
                    </div>
                </div>
                <div class="lr_e">
                    <label><span class="err" style="display:none" id="username_err">请输入布谷科技帐号</span>帐号</label>
                    <div class="txt" style="z-index:9999;position:relative;">
                        <input tabindex="1" class="ef" maxlength=100 id="username" name="username" type="text" autocomplete='off'
                               placeholder="请输入员工号" value="${username}">
                               <%--onkeyup="show_emaillist(this, event)"--%>
                               <%--onkeydown="enterClickGetValue(event);--%>
                        <div class="ul" style="display:none;top:40px" id="email_list">
                        </div>
                    </div>
                </div>
                <div class="lr_e">
                    <label><span class="err" style="display:none" id="pwd_err">请输入密码</span>密码</label>
                    <div class="txt">
                        <input tabindex="2" class="ef" autocomplete='off' id="password" name="password" type="password"
                               placeholder="请输入密码" value="">
                    </div>
                </div>
                <div class="lr_e">
                    <label><span class="err" style="display:none" id="pwd_confirm_err">请再次输入密码</span>密码确认</label>
                    <div class="txt">
                        <input tabindex="2" class="ef" autocomplete='off' id="password_confirm" name="password_confirm" type="password"
                               placeholder="请再次输入密码" value="">
                    </div>
                </div>
                <div class="lr_e e2" style="display:none" id="verifypic">
                    <label><span class="err" id="verifycode_err" style="display:none" id="verify_err">图形验证码错误或已过期</span>图形验证码</label>
                    <div class="txt">
                        <input class="ef" autocomplete='off' id="verifycode" maxlength=4 name="verifycode" type="text"
                               placeholder="不区分大小写，点击图片可刷新" value="" onkeyup="chkverifycode()">
                        <input type="hidden" id="verifycodechked" value="0"/>
                        <span class="icon_ok" style="display:none" id="verifycode_ok"></span>
                    </div>
                    <img class="ac verifyPicChangeClick" onclick="changeVerifyCode()" id="verifyPic_img" type="3"
                         align="absmiddle" src="" width="100" height="42">
                </div>
                <%--<div class="lr_ok">--%>
                    <%--<a tabindex="5" class="a"--%>
                       <%--href="/user/forgetPassword.do">忘记密码？</a>--%>
                    <%--<input tabIndex="4" type="checkbox" name="rememberMe" id="rememberMe" ${rememberMe == 0 ? "checked" : ""}>--%>
                    <%--<label for="rememberMe">记住我</label>--%>
                <%--</div>--%>
                <div class="btnbox">
                    <button tabIndex="6" type='button' class="p_but" id="register_btn" tabindex="10">注  册</button>
                </div>
            </form>
            <%--<div class="lr_p">--%>
                <%--还不是会员？<a tabindex="7" class="a2"--%>
                         <%--href="/toRegister.do">免费注册</a>--%>
            <%--</div>--%>
            <div class="mind">
                温馨提示：为了避免耽误您的考试，遇到问题请及时联系管理教师。 <a tabindex="8" class="a" target="_blank"
                                                  href="#">查看详情</a>
            </div>

        </div>
    </div>
    <div class="lr_bm"></div>
</div>
<div class="footer f2">
    <div class="in">
        <p class="note nag">
            <span>未经布谷科技同意，不得转载本网站信息及作品 | 布谷科技版权所有&copy;1999-2017</span>

        </p></div>
</div>
<script language="javascript">
    $(document).ready(function () {
        var scode = $("#scode").val();
        if (scode != "") {
            var str = $("#loginname").val();
            $("#loginname").val("").focus().val(str);
        }


        $("#login_btn").on("click", function () {
            var name = $("#name").val().trim();
            var username = $("#username").val().trim();
            var password = $("#password").val().trim();
            var password_confirm = $("#password_confirm").val().trim();
            if(name == ''){
                $("#name_err").show();
                return false;
            }
            if(username == ""){
                return false;
            }
            if(password == ""){
                return false;
            }
            if(password != password_confirm){
                $("#errmsg").text("两次密码输入不一致");
                return false;
            }
            $.ajax({
                url: "/register.do",
                type: "post",
                data: {username: username, password: password, name: name},
                success: function (data) {
                    if(data == 0){
                        //登陆成功，跳转页面
                        window.location.href = "/index.do";
                    }else{
                        $(".errbox").show();
                    }

                },
                error: function (data) {
                    //如果有验证码就修改验证码
                    console.log("登录失败")
                }
                
            })
        })

        var input_arr = ['username', 'password', 'verifycode', 'useremail', 'userpwd', 'cfmpwd', 'phone', 'phonecode', 'name', 'newpwd', 'newpwd_cfm', 'oldpwd', 'newpwdcfm', 'useraccount', 'emailverifycode', 'messagecode'];
        $.each(input_arr, function (i, item) {
            $("#" + item).focus(function () {
                $("#" + item).parent().addClass("focusinput");
            });
        });


        var input_arr = ['username', 'password', 'verifycode', 'userpwd', 'cfmpwd', 'phone', 'phonecode', 'name', 'newpwd', 'newpwd_cfm', 'oldpwd', 'newpwdcfm', 'emailverifycode', 'messagecode'];
        $.each(input_arr, function (i, item) {
            $("#" + item).blur(function () {
                $("#" + item).parent().removeClass("focusinput");
            });
        });
    });

</script>
</body>
</html>
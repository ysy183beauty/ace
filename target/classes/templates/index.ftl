<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>测试管理系统</title>
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!--引入css与js -->
    <#include "common/frame/common.ftl" />
    <script type="text/javascript">
        $(function () {
            $('#menu').sidebarMenu({
                data: [{
                    id: '1',
                    text: '系统设置',
                    icon: 'glyphicon glyphicon-wrench',
                    url: '',
                    menus: [{
                        id: '11',
                        text: '基本信息配置',
                        icon: 'glyphicon glyphicon-th-list',
                        url: '/sys/view/toSettingList'
                    },{
                        id: '112',
                        text: '配置信息列表',
                        icon: 'glyphicon glyphicon-th-list',
                        url: '/sys/view/toSelectSetting'
                    }]
                }, {
                    id: '2',
                    text: '业务数据',
                    icon: 'glyphicon glyphicon-leaf',
                    url: '',
                    menus: [{
                        id: '21',
                        text: '基本信息列表',
                        icon: 'glyphicon glyphicon-tasks',
                        url: '/business/view/selectBusinessInfo'
                    }, {
                        id: '22',
                        text: '组织列表',
                        icon: 'glyphicon glyphicon-tasks',
                        url: '/business/view/toOrgList'
                    }]
                }]
            });
        });
    </script>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default ace-save-state">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class="navbar-header pull-left">
            <a href="index.html" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    测试管理系统
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${ctx}/static/assets/images/avatars/user.jpg" alt="Jason's Photo" />
                        <span class="user-info">
									<small>欢迎界面,</small>
									Admin
								</span>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="ace-icon fa fa-cog"></i>
                                设置
                            </a>
                        </li>
                        <li>
                            <a href="profile.html">
                                <i class="ace-icon fa fa-user"></i>
                                关于我们
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <i class="ace-icon fa fa-power-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>

<div class="main-container ace-save-state" id="main-container">
    <div id="sidebar" class="sidebar responsive ace-save-state">
        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>
                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>
            </div>
            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span>
                <span class="btn btn-info"></span>
                <span class="btn btn-warning"></span>
                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->
        <!--引入左边菜单 -->
        <ul class="nav nav-list" id="menu">

        </ul>
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

    <div class="main-content">
        <div class="main-content-inner content-wrapper" id="content-wrapper">
            <!-- nav tabs -->
            <div class="content-tabs">
                <button class="roll-nav roll-left tabLeft" role="rollleft">
                    <a href="javascript:void(0);" >
                        <i class="glyphicon glyphicon-fast-backward"></i>
                    </a>
                </button>
                <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">
                    <ul class="page-tabs-content nav nav-tabs" style="margin-left: 0px;">
                        <li role="presentation" id="tab_tab_home1" aria-url="example/ajax/mailbox.txt" aria-ajax="false" class="menu_tab active">
                            <a href="#tab_home1" aria-controls="tab_home1" role="tab" data-toggle="tab">首页</a>
                            <i class="close-tab glyphicon glyphicon-remove" style="display: none;"></i>
                        </li>
                    </ul>
                </nav>
                <button class="roll-nav roll-right tabRight" role="roleright">
                    <a href="javascript:void(0);">
                        <i class="glyphicon glyphicon-fast-forward"></i>
                    </a>
                </button>
                <button class="roll-nav roll-right fullscreen" role = "fullscreen">
                    <i class="glyphicon glyphicon-fullscreen"></i>
                </button>
            </div>
            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane content active" id="tab_home1" role="tabpanel">
                    I'm a homepage.
                </div>
            </div>
        </div>
    </div>
    <!-- 引入底部栏-->
    <#include "common/frame/footer.ftl" />
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>
</body>
</html>

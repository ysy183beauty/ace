/**
 * Website: http://git.oschina.net/hbbcs/bootStrap-addTabs
 *
 * Version : 2.1
 *
 * Created by joe on 2016-2-4.Update 2017-10-24
 */

(function ($) {

    var settings = {
        /**
         * 直接指定所有页面TABS内容
         * @type {String}
         */
        content: '',
        /**
         * 是否可以关闭
         * @type {Boolean}
         */
        close: true,
        /**
         * 监视的区域
         * @type {String}
         */
        monitor: 'body',
        /**
         * 默认使用iframe还是ajax,true 是iframe,false是ajax
         * @type {Boolean}
         */
        iframe: false,
        /**
         * 固定TAB中IFRAME高度,根据需要自己修改
         * @type {Number}
         */
        height: $(window).height() - 148,
        /**
         * 目标
         * @type {String}
         */
        target: '.content-tabs',
        /**
         * 显示加载条
         * @type {Boolean}
         */
        loadbar: true,
        /**
         * 是否使用右键菜单
         * @type {Boolean}
         */
        contextmenu: true,
        /**
         * 将打开的tab页记录到本地中，刷新页面时自动打开，默认不使用
         * @type {Boolean}
         */
        store: false,
        /**
         * 保存的项目名称，为了区分项目
         * @type {String}
         */
        storeName: '',
        /**
         * 内容样式表
         * @type {String}
         */
        contentStyle: 'content',
        /**
         * ajax 的参数
         * @type {Object}
         */
        ajax: {
            'async': true,
            'dataType': 'html',
            'type': 'get'
        },
        /**
         *
         * @type {Object}
         */
        local: {
            'refreshLabel': '刷新此标签',
            'closeThisLabel': '关闭此标签',
            'closeOtherLabel': '关闭其他标签',
            'closeLeftLabel': '关闭左侧标签',
            'closeRightLabel': '关闭右侧标签',
            'closeAllLabel': '全部关闭',
            'loadbar': '正在加载内容，请稍候．．．'
        },
        /**
         * 关闭tab回调函数
         * @return {[type]} [description]
         */
        callback: function () {
        }
    };

    var target;

	// 本地缓存：缓存TAB
    _store = function () {
        if (typeof (arguments[0]) == 'object') {
            arguments[0].each(function (name, val) {
                localStorage.setItem(name, val);
            })
        } else if (arguments[1]) {
            localStorage.setItem(arguments[0], arguments[1]);
        } else {
            return localStorage.getItem(arguments[0]);
        }
    }

	// 点击事件：添加TAB
    _click = function (obj) {
        var a_obj, a_target;
		// obj = sidebar被点击的标签元素<a>
		
        a_obj = (typeof obj.data('addtab') == 'object') ? obj.data('addtab') : obj.data();
		// a_obj = {addtab: "mail", url: "example/ajax/mailbox.txt"}
        // a_obj = {addtab: "mail1", ajax: true, url: "example/ajax/mailbox.txt"}
        if (!a_obj.id && !a_obj.addtab) {
            a_obj.id = Math.random().toString(36).substring(3, 35);
            obj.data('id', a_obj.id);
        }

        $.addtabs.add({
            'target': a_obj.target ? a_obj.target : target, // .nav-my-tab
            'id': a_obj.id ? a_obj.id : a_obj.addtab, // a_obj.addtab
            'title': a_obj.title ? a_obj.title : obj.html(), // obj.html()
            'content': settings.content ? settings.content : a_obj.content,
            'url': a_obj.url ? a_obj.url : obj.attr('href'),
            'ajax': a_obj.ajax ? a_obj.ajax : false
        });
    };

    _createMenu = function (right, icon, text) {
        return $('<a>', {
            'href': 'javascript:void(0);',
            'class': "list-group-item",
            'data-right': right
        }).append(
            $('<i>', {
                'class': 'glyphicon ' + icon
            })
        ).append(text);
    }

    _pop = function (id, e, mouse) {
        $('body').find('#popMenu').remove();
        var refresh = e.attr('id') ? _createMenu('refresh', 'glyphicon-refresh', settings.local.refreshLabel) : '';
        var remove = e.attr('id') ? _createMenu('remove', 'glyphicon-remove', settings.local.closeThisLabel) : '';
        var left = e.prev('li').attr('id') ? _createMenu('remove-left', 'glyphicon-chevron-left', settings.local.closeLeftLabel) : '';
        var right = e.next('li').attr('id') ? _createMenu('remove-right', 'glyphicon-chevron-right', settings.local.closeRightLabel) : '';
        var popHtml = $('<ul>', {
            'aria-controls': id,
            'class': 'rightMenu list-group',
            id: 'popMenu',
            'aria-url': e.attr('aria-url'),
            'aria-ajax': e.attr('aria-ajax')
        }).append(refresh)
            .append(remove)
            .append(_createMenu('remove-circle', 'glyphicon-remove-circle', settings.local.closeOtherLabel))
            .append(_createMenu('remove-all', 'glyphicon-trash', settings.local.closeAllLabel))
            .append(left)
            .append(right);

        popHtml.css({
            'top': mouse.pageY,
            'left': mouse.pageX
        });
        popHtml.appendTo($('body')).fadeIn('slow');
        //刷新页面
        $('ul.rightMenu a[data-right=refresh]').on('click', function () {
            var id = $(this).parent('ul').attr("aria-controls").substring(4);
            var url = $(this).parent('ul').attr('aria-url');
            var ajax = $(this).parent('ul').attr('aria-ajax');
            $.addtabs.add({
                'id': id,
                'url': url,
                'refresh': true,
                'ajax': ajax
            });
        });

        //关闭自身
        $('ul.rightMenu a[data-right=remove]').on('click', function () {
            var id = $(this).parent("ul").attr("aria-controls");
            if (id.substring(0, 4) != 'tab_') return;
            $.addtabs.close({
                "id": id
            });
            // $.addtabs.drop();
        });

        //关闭其他
        $('ul.rightMenu a[data-right=remove-circle]').on('click', function () {
            var tab_id = $(this).parent('ul').attr("aria-controls");
            console.log(tab_id)
            target.find('li').each(function () {
                var id = $(this).attr('id');
                if (id && id != 'tab_' + tab_id) {
                    $.addtabs.close({
                        "id": $(this).children('a').attr('aria-controls')
                    });
                }
            });
            _scrollToTab(false, $('#tab_' + tab_id))
            // $.addtabs.drop();
        });

        //关闭左侧
        $('ul.rightMenu a[data-right=remove-left]').on('click', function () {
            var tab_id = $(this).parent('ul').attr("aria-controls");
            $('#tab_' + tab_id).prevUntil().each(function () {
                var id = $(this).attr('id');
                if (id && id != 'tab_' + tab_id) {
                    $.addtabs.close({
                        "id": $(this).children('a').attr('aria-controls')
                    });
                }
            });
            _scrollToTab(false, $('#tab_' + tab_id))
            // $.addtabs.drop();
        });

        //关闭右侧
        $('ul.rightMenu a[data-right=remove-right]').on('click', function () {
            var tab_id = $(this).parent('ul').attr("aria-controls");
            $('#tab_' + tab_id).nextUntil().each(function () {
                var id = $(this).attr('id');
                if (id && id != 'tab_' + tab_id) {
                    $.addtabs.close({
                        "id": $(this).children('a').attr('aria-controls')
                    });
                }
            });
            _scrollToTab(false, $('#tab_' + tab_id))
            // $.addtabs.drop();
        });
        
        //关闭全部
        $('ul.rightMenu a[data-right=remove-all]').on('click', function () {
        	var tab_id = $(this).parent('ul').attr("aria-controls");
        	$.addtabs.closeAll(null);
        });
        
        popHtml.mouseleave(function () {
            $(this).fadeOut('slow');
        });
        $('body').click(function () {
            popHtml.fadeOut('slow');
        })
    };

    _listen = function () {
    	// 给nav-list列表中有data-addtab属性的a标签绑定点击事件
        $(settings.monitor).on('click', '[data-addtab]', function () {
        	/**
        	 * 考虑到sidebar快照的情况，换成以下方式调整active
        	 */
        	$('.nav-list').find('.active').removeClass('active');
			$(this).parents("li").addClass('active').siblings('li').removeClass('active');
        	$(this).closest("li").addClass('active').siblings().removeClass('active');
        	
            _click($(this));
            _scrollToTab(true, (this));
            // $.addtabs.drop();
        });

		// jquery的事件委托，将关闭标签的事件委托到body上
        $('body').on('click', '.close-tab', function () {
            var id = $(this).prev("a").attr("aria-controls");
            $.addtabs.close({
                'id': id
            });
            // $.addtabs.drop();
        });

		// jQuery事件委托，鼠标移入显示关闭按钮
        $('body').on('mouseover', 'li[role = "presentation"]', function () {
            $(this).find('.close-tab').show();
        });
		
		// jQuery事件委托，鼠标移出隐藏关闭按钮
        $('body').on('mouseleave', 'li[role = "presentation"]', function () {
            $(this).find('.close-tab').hide();
        });
        
        // jQuery事件委托，左滚事件
        $('body').on('click', 'button[role = "rollleft"]', function () {
            _scrollTabLeft();
        });
        
        // jQuery事件委托，右滚事件
        $('body').on('click', 'button[role = "roleright"]', function () {
            _scrollTabRight();
        });
        
        // 全屏事件委托
        $('body').on('click', 'button[role = "fullscreen"]', function () {
            _fullScreen();
        });
        
        if (settings.contextmenu) {
            //obj上禁用右键菜单
            $('body').on('contextmenu', 'li[role=presentation]', function (e) {
                var id = $(this).children('a').attr('aria-controls');
                _pop(id, $(this), e);
                return false;
            });
        }

		// TAB拖拽事件
        var el;
        $('body').on('dragstart.h5s', '.nav-tabs li', function (e) {
            el = $(this);
            //清除拖动操作携带的数据，否者在部分浏览器上会打开新页面
            if(e.originalEvent && e.originalEvent.dataTransfer
                && 'function' == typeof e.originalEvent.dataTransfer.clearData){
                e.originalEvent.dataTransfer.clearData();
            }
        }).on('dragover.h5s dragenter.h5s drop.h5s', '.nav-tabs li', function (e) {
            if (el == $(this)) return;
            $('.dragBack').removeClass('dragBack');
            $(this).addClass('dragBack');
            //支持前后调整标签顺序
            if (el.index() < $(this).index()) {
                el.insertAfter($(this))
            } else {
                $(this).insertAfter(el)
            }
        }).on('dragend.h5s', '.nav-tabs li', function () {
            $('.dragBack').removeClass('dragBack');
        });

        $('body').on('shown.bs.tab', 'a[data-toggle="tab"]', function () {
            var id = $(this).parent('li').attr('id');
            id = id ? id.substring(8) : '';
            if (settings.store) {
                var tabs = $.parseJSON(_store('addtabs'+settings.storeName));
                $.each(tabs, function (k, t) {
                    (t.id == id) ?(t.active = 'true'):(delete t.active);
                });
                tabs = JSON.stringify(tabs);
                _store('addtabs'+settings.storeName, tabs);
            }
        });

        //浏览器大小改变时自动收放tab
        $(window).on('resize', function() {
            // $.addtabs.drop();
        });
    };

    $.addtabs = function (options) {
        $.addtabs.set(options);
        _listen();
        if (settings.store) {
            var tabs = _store('addtabs'+settings.storeName) ? $.parseJSON(_store('addtabs'+settings.storeName)) : {};
            var active;
            $.each(tabs, function (k, t) {
                if (t.active) active = k;
                $.addtabs.add(t);
            });
            if (active) {
              target.children('.active').removeClass('active');
              $('#tab_' + active).addClass('active');
              target.next('.tab-content').children('.active').removeClass('active');
              $('#' + active).addClass('active');
            }
        }
    };

    $.addtabs.set = function () {
        if (arguments[0]) {
            if (typeof arguments[0] == 'object') {
                settings = $.extend(settings, arguments[0] || {});
            } else {
                settings[arguments[0]] = arguments[1];
            }
        }
        if (typeof settings.target == 'object') {
            target = settings.target;
        } else {
            target = $('body').find(settings.target).length > 0 ? $(settings.target).first() : $('body').find('.content-tabs').first();
        }
    }

    $.addtabs.add = function (opts) {
    	/*
    	 {target: init(1), id: "mail", title: "↵                                            mailbox↵                                        ", content: undefined, url: "example/ajax/mailbox.txt", …}
			ajax: false
			content: undefined
			id: "mail"
			target: init [ul.nav-my-tab, prevObject: init(1), context: document]
			title: "↵                                            mailbox↵                                        "
			url: "example/ajax/mailbox.txt"
			__proto__: Object
    	 * */
        var a_target, content;
        opts.id = opts.id ? opts.id : Math.random().toString(36).substring(3, 35);
        if (typeof opts.target == 'object') {
            a_target = opts.target;
        } else if (typeof opts.target == 'string') {
            a_target = $('body').find(opts.target).first();
        } else {
            a_target = target;
        }
        var id = 'tab_' + opts.id;
        var tab_li = a_target;
        //写入cookie，默认不使用
        if (settings.store) {
          var tabs = _store('addtabs'+settings.storeName) ? $.parseJSON(_store('addtabs'+settings.storeName)) : {};
            tabs[id] = opts;
            tabs[id].target = (typeof tabs[id].target == 'object') ? settings.target : tabs[id].target;
            $.each(tabs, function (k, t) {
                delete t.active;
            });
            tabs[id].active = 'true';
            tabs = JSON.stringify(tabs);
            _store('addtabs'+settings.storeName, tabs);
        }

        var tab_content = tab_li.next('.tab-content');
	
        tab_li.find(".page-tabs-content").children('li[role = "presentation"].active').removeClass('active');
        tab_content.children('div[role = "tabpanel"].active').removeClass('active');
        //如果TAB不存在，创建一个新的TAB
        if (tab_li.find('#tab_' + id).length < 1) {
            var cover = $('<div>', {
                'id': 'tabCover',
                'class': 'tab-cover'
            });
            //创建新TAB的title
            var title = $('<li>', {
                'role': 'presentation',
                'id': 'tab_' + id,
                'aria-url': opts.url,
                'aria-ajax': opts.ajax ? true : false,
                'class': 'menu_tab'
            }).append(
                $('<a>', {
                    'href': '#' + id,
                    'aria-controls': id,
                    'role': 'tab',
                    'data-toggle': 'tab'
                }).html(opts.title)
            );

            //是否允许关闭
            if (settings.close) {
                title.append(
                    $('<i>', {
                        'class': 'close-tab glyphicon glyphicon-remove'
                    })
                );
            }
            //创建新TAB的内容
            var content = $('<div>', {
                'class': 'tab-pane ' + settings.contentStyle,
                'id': id,
                'height': settings.height - 75,
                'role': 'tabpanel'
            });

            //加入TABS
            tab_li.find(".page-tabs-content").append(title);
            tab_content.append(content.append(cover));
        } else if (!opts.refresh) {
            $('#tab_' + id).addClass('active');
            $('#' + id).addClass('active');
            return;
        } else {
            content = $('#' + id);
            content.html('');
        }
        //加载条
        if (settings.loadbar) {
            content.html($('<div>', {
                'class': ''
            }).append(
                $('<div>', {
                    'class': 'progress-bar progress-bar-striped progress-bar-success active',
                    'role': 'progressbar',
                    'aria-valuenow': '100',
                    'aria-valuemin': '0',
                    'aria-valuemax': '100',
                    'style': 'width:100%;position:absolute;top:0px;left:0px;height:16px;line-height:16px;'
                }).append('<span class="sr-only">100% Complete</span>')
                    .append('<span>' + settings.local.loadbar + '</span>')
            ));
        }

        //是否指定TAB内容
        if (opts.content) {
            content.html(opts.content);
        } else if (settings.iframe == true && (opts.ajax == 'false' || !opts.ajax)) { //没有内容，使用IFRAME打开链接
            content.html(
                $('<iframe>', {
                    'class': 'iframeClass',
                    'height': settings.height,
                    'frameborder': "no",
                    'border': "0",
                    'src': opts.url
                })
            );
        } else {
            var ajaxOption = $.extend(settings.ajax, opts.ajax || {});
            ajaxOption.url = opts.url;
            ajaxOption.error = function(XMLHttpRequest, textStatus) { content.html(XMLHttpRequest.responseText); };
            ajaxOption.success = function (result) {
                content.html(result);
            }
            $.ajax(ajaxOption);
        }

        //激活TAB

        tab_li.find(".page-tabs-content").find('#tab_' + id).addClass('active');
        tab_content.find('#' + id).addClass('active');
        tab_content.find('#' + id).find('#tabCover').remove();
    };

    $.addtabs.close = function (opts) {
    	// opts = {id: "tab_message"}
        //如果关闭的是当前激活的TAB，激活他的前一个TAB
        if ($("#tab_" + opts.id).hasClass('active')) {
            if ($('#tab_' + opts.id).parents('li.tabdrop').length > 0 && !$('#tab_' + opts.id).parents('li.tabdrop').hasClass('hide')) {
                $('#tab_' + opts.id).parents('.nav-tabs').find('li').last().tab('show');
            } else {
                $("#tab_" + opts.id).prev('li').tab('show');
            }
            $("#" + opts.id).prev().addClass('active');
        }
        //关闭TAB
        $("#tab_" + opts.id).remove();
        // 关闭TAB对应的content
        $("#" + opts.id).remove();
        if (settings.store) {
            var tabs = $.parseJSON(_store('addtabs'+settings.storeName));
            delete tabs[opts.id];
            tabs = JSON.stringify(tabs);
            _store('addtabs'+settings.storeName, tabs);
        }
        // $.addtabs.drop();
        settings.callback();
    };

    $.addtabs.closeAll = function (target) {
        if (typeof target == 'string') {
            target = $('body').find(target);
        } else {
        	target = $('body').find(settings.target)
        }
        
        $.each(target.find('li[id]'), function () {
            var id = $(this).children('a').attr('aria-controls');
            $("#tab_" + id).remove();
            $("#" + id).remove();
        });
        // $.addtabs.drop();
    };

    $.addtabs.drop = function () {
        //创建下拉标签
        var dropdown = $('<li>', {
            'class': 'dropdown pull-right hide tabdrop tab-drop'
        }).append(
            $('<a>', {
                'class': 'dropdown-toggle',
                'data-toggle': 'dropdown',
                'href': '#'
            }).append(
                $('<i>', {
                    'class': "glyphicon glyphicon-align-justify"
                })
            ).append(
                $('<b>', {
                    'class': 'caret'
                })
            )
        ).append(
            $('<ul>', {
                'class': "dropdown-menu"
            })
        )


        $('body').find('.nav-tabs').each(function () {
            var element = $(this);
            //检测是否已增加
            if (element.find('.tabdrop').length < 1) {
                dropdown.prependTo(element);
            } else {
                dropdown = element.find('.tabdrop');
            }
            //检测是否有下拉样式
            if (element.parent().is('.tabs-below')) {
                dropdown.addClass('dropup');
            }
            var collection = 0;

            //检查超过一行的标签页
            element.append(dropdown.find('li'))
                .find('>li')
                .not('.tabdrop')
                .each(function () {
                    if (this.offsetTop > 0 || element.width() - $(this).position().left - $(this).width() < 83) {
                        dropdown.find('ul').prepend($(this));
                        collection++;
                    }
                });

            //如果有超出的，显示下拉标签
            if (collection > 0) {
                dropdown.removeClass('hide');
                if (dropdown.find('.active').length == 1) {
                    dropdown.addClass('active');
                } else {
                    dropdown.removeClass('active');
                }
            } else {
                dropdown.addClass('hide');
            }
        })

    };
    
    // 计算多个jq对象的宽度和
	_calSumWidth = function (element) {
	    var width = 0;
	    $(element).each(function () {
	        width += $(this).outerWidth(true);
	    });
	    return width;
	};
    
    // 调整tab位置
    _scrollToTab = function (flag, element) { // flag标志是sidebar点击事件||关闭-系列点击事件
    	//element是tab(a标签， class='menu_tab'),装有标题那个，这里a标签用li标签代替
    	if(flag) {
    		element = $('.page-tabs-content').find('.active');
    	}
	    //div.content-tabs > div.page-tabs-content
	    var marginLeftVal = _calSumWidth($(element).prevAll()),//前面所有tab的总宽度
	        marginRightVal = _calSumWidth($(element).nextAll());//后面所有tab的总宽度
	    //一些button(向左,向右,全屏)的总宽度
	    var tabOuterWidth = _calSumWidth($(".content-tabs").children().not(".menuTabs"));
	    // tab(a标签)显示区域的总宽度
	    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
	    //将要滚动的长度
	    var scrollVal = 0;
	    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
	        //所有的tab都可以显示的情况
	        scrollVal = 0;
	    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
	        //向右滚动  <--
	        //marginRightVal(后面所有tab的总宽度) 小于可视区域-(当前tab和下一个tab的宽度)
	        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
	            scrollVal = marginLeftVal;
	            var tabElement = element;
	            while ((scrollVal - $(tabElement).outerWidth() - $(element).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
	                scrollVal -= $(tabElement).prev().outerWidth();
	                tabElement = $(tabElement).prev();
	            }
	        }
	    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
	        //向左滚动 -->
	        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
	    }
	    //执行动画
	    $('.page-tabs-content').animate({
	        marginLeft: 0 - scrollVal + 'px'
	    }, "fast");
	};
	
	// 滚动条滚动到左边
	_scrollTabLeft = function () {
	    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
	    var tabOuterWidth = _calSumWidth($(".content-tabs").children().not(".menuTabs"));
	    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
	    var scrollVal = 0;
	    if ($(".page-tabs-content").width() < visibleWidth) {
	        return false;
	    } else {
	        var tabElement = $(".menu_tab:first");
	        var offsetVal = 0;
	        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
	            offsetVal += $(tabElement).outerWidth(true);
	            tabElement = $(tabElement).next();
	        }
	        offsetVal = 0;
	        if (_calSumWidth($(tabElement).prevAll()) > visibleWidth) {
	            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
	                offsetVal += $(tabElement).outerWidth(true);
	                tabElement = $(tabElement).prev();
	            }
	            scrollVal = _calSumWidth($(tabElement).prevAll());
	        }
	    }
	    $('.page-tabs-content').animate({
	        marginLeft: 0 - scrollVal + 'px'
	    }, "fast");
	};
	// 滚动条滚动到右边
	_scrollTabRight = function () {
	    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
	    var tabOuterWidth = _calSumWidth($(".content-tabs").children().not(".menuTabs"));
	    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
	    var scrollVal = 0;
	    if ($(".page-tabs-content").width() < visibleWidth) {
	        return false;
	    } else {
	        var tabElement = $(".menu_tab:first");
	        var offsetVal = 0;
	        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
	            offsetVal += $(tabElement).outerWidth(true);
	            tabElement = $(tabElement).next();
	        }
	        offsetVal = 0;
	        while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
	            offsetVal += $(tabElement).outerWidth(true);
	            tabElement = $(tabElement).next();
	        }
	        scrollVal = _calSumWidth($(tabElement).prevAll());
	        if (scrollVal > 0) {
	            $('.page-tabs-content').animate({
	                marginLeft: 0 - scrollVal + 'px'
	            }, "fast");
	        }
	    }
	};
	
	_fullScreen = function() {
		var isFullScreen = false;
    
	    var handleFullScreen = function () {
	        if (isFullScreen) {
	            exitFull();
	            isFullScreen = false;
	        } else {
	            requestFullScreen();
	            isFullScreen = true;
	        }
	    };
	    
		var requestFullScreen = function () {
	        var de = document.documentElement;
	
	        if (de.requestFullscreen) {
	            de.requestFullscreen();
	        } else if (de.mozRequestFullScreen) {
	            de.mozRequestFullScreen();
	        } else if (de.webkitRequestFullScreen) {
	            de.webkitRequestFullScreen();
	        }
	        else {
	            alert("该浏览器不支持全屏！");
	        }
	
	    };
	    
	    var requestFullScreen2 = function (element) {
	        // 判断各种浏览器，找到正确的方法
	        var requestMethod = element.requestFullScreen || //W3C
	            element.webkitRequestFullScreen ||    //Chrome等
	            element.mozRequestFullScreen || //FireFox
	            element.msRequestFullScreen; //IE11
	        if (requestMethod) {
	            requestMethod.call(element);
	        }
	        else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
	            var wscript = new ActiveXObject("WScript.Shell");
	            if (wscript !== null) {
	                wscript.SendKeys("{F11}");
	            }
	        }
	    };
	
	    //退出全屏 判断浏览器种类
	    var exitFull = function () {
	        // 判断各种浏览器，找到正确的方法
	        var exitMethod = document.exitFullscreen || //W3C
	            document.mozCancelFullScreen ||    //Chrome等
	            document.webkitExitFullscreen || //FireFox
	            document.webkitExitFullscreen; //IE11
	        if (exitMethod) {
	            exitMethod.call(document);
	        }
	        else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
	            var wscript = new ActiveXObject("WScript.Shell");
	            if (wscript !== null) {
	                wscript.SendKeys("{F11}");
	            }
	        }
	    };
	    
	    handleFullScreen();
	};
	
	
})(jQuery);

$(function () {
    $.addtabs();
})

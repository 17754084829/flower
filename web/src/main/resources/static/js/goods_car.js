var goodsList = [
]
/**
 * {
		id: 1234564876,
		imgUrl: 'img/1.png',
		goodsInfo: '号地块健身房回复的科技示范户快速坚实的看了看大家发快递了很费劲的开始放假',
		goodsParams: '四季度后付款的酸辣粉',
		price: 199,
		goodsCount: 1,
		singleGoodsMoney: 199
	},
 {
		id: 1234564876,
		imgUrl: 'img/2.png',
		goodsInfo: '号地块健身房回复的科技示范户快速坚实的看了看大家发快递了很费劲的开始放假',
		goodsParams: '四季度后付款的酸辣粉',
		price: 299,
		goodsCount: 2,
		singleGoodsMoney: 598
	},
 {
		id: 1234564876,
		imgUrl: 'img/3.png',
		goodsInfo: '号地块健身房回复的科技示范户快速坚实的看了看大家发快递了很费劲的开始放假',
		goodsParams: '四季度后付款的酸辣粉',
		price: 399,
		goodsCount: 1,
		singleGoodsMoney: 399
	}
 *
 * **/
var deleteGoods = null
$(document).ready(function () {
	$.ajax({
		url:"/admin/getGoodsCar",
		success:function (data) {
			if(data.code==200){
				for(var index=0;index<data.data.length;index++){
					var obj={};
					obj.id=data.data[index].id;
					obj.imgUrl=data.data[index].goods_image_url;
					obj.goodsInfo=data.data[index].goods_desc;
					obj.goodsParams=data.data[index].goods_type;
					obj.goodsCount=data.data[index].goods_num;
					obj.price=data.data[index].goods_price;
					obj.singleGoodsMoney=obj.price*obj.goodsCount;
					goodsList.push(obj);
				}

				loadGoods();
			}else {
				swal("提示信息","转态失效","error");
			}

		}
	});
	getAddrs();
	$(".pro").click(function () {
		pro=$(this).html();
		$("#city").click();
	});

	$(".cit").click(function () {
		city=$(this).html();
		$("#dis").click();
	});
	$(".dis").click(function () {
		dis=$(this).html();
		$("#all").click();
	});
	$('#myTab a').click(function(){
		$(this).tab('show');
	});

	$("#addr").on("change",function () {
		if($("#addr").val()=="add"){
			$("#add_addr").show();
			$("#add_addr_laber").show();

		}
	})

});
function loadGoods() {
	$.each(goodsList, function(i, item) {
		var goodsHtml = '<div class="goods-item">' +
			'<div class="panel panel-default">' +
			'<div class="panel-body">' +
			'<div class="col-md-1 car-goods-info">' +
			'<label><input  id="'+item.id+'" type="checkbox" value="'+item.id+'" class="goods-list-item"/></label>' +
			'</div>' +
			'<div class="col-md-3 car-goods-info goods-image-column">' +
			'<img class="goods-image" src="' + item.imgUrl + '" style="width: 100px; height: 100px;" />' +
			'<span id="goods-info">' +
			item.goodsInfo +
			'</span>' +
			'</div>' +
			'<div class="col-md-3 car-goods-info goods-params">' + item.goodsParams + '</div>' +
			'<div class="col-md-1 car-goods-info goods-price"><span>￥</span><span class="single-price">' + item.price + '</span></div>' +
			'<div class="col-md-1 car-goods-info goods-counts">' +
			'<div class="input-group">' +
			'<div class="input-group-btn">' +
			'<button type="button" class="btn btn-default car-decrease" value="'+item.id+'">-</button>' +
			'</div>' +
			'<span class="form-control goods-count" id="span'+item.id+'">'+item.goodsCount+'</span>'+
			'<div class="input-group-btn">' +
			'<button type="button" class="btn btn-default car-add" value="'+item.id+'">+</button>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'<div class="col-md-1 car-goods-info goods-money-count"><span>￥</span><span class="single-total">' + item.singleGoodsMoney + '</span></div>' +
			'<div class="col-md-2 car-goods-info goods-operate">' +
			'<button type="button" onclick="deleteGoodsById(\''+item.id+'\')" class="btn btn-danger item-delete">删除</button>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>'
		$('.goods-content').append(goodsHtml);



		$('#check-goods-all').on('change', function() {
			if(this.checked) {
				$('#checked-all-bottom').prop('checked', true)
			} else {
				$('#checked-all-bottom').prop('checked', false)
			}
			checkedAll(this)
		})
		$('#checked-all-bottom').on('change', function() {
			if(this.checked) {
				$('#check-goods-all').prop('checked', true)
			} else {
				$('#check-goods-all').prop('checked', false)
			}
			checkedAll(this)
		})
		$('input.goods-list-item').on('change', function() {
			if($(this).val()===$('input.goods-list-item:first').val()) {
				var flag = $('input.goods-list-item')[0].checked;
				if (flag && is_once) {
					is_once = false;
					return;
				} else
					is_once = true;
				if (!flag && shut_down) {
					shut_down = false;
					return;
				} else
					shut_down = true;
			}
			var id="#span"+$(this).val();
			var tmpCheckEl = $(id);
			var checkEvent = new ShoppingCarObserver(tmpCheckEl, null)
			checkEvent.checkedChange()
			checkEvent.checkIsAll()
		});
		$('.goods-content').on('click', '.car-decrease', function() {
			var goodsInput = $(this).parents('.input-group').find('.goods-count')
			var decreaseCount = new ShoppingCarObserver(goodsInput, false);
			var id="#span"+$(this).val().trim();
			var num=parseInt($(id).html())-1;
			if(num<0)
				return;
			$.ajax({
				url:"/admin/addCarnum",
				data:{"id":$(this).val().trim(),"goods_num":num},
				success:function (data) {
					if(data.code==200){
						decreaseCount.showCount()
						decreaseCount.computeGoodsMoney()
					}else{
						swal("提示信息","添加到购物车失败,请检查下登录状态","error");
					}
				}
			});
		})
		$('.goods-content').on('click', '.car-add', function() {
			var goodsInput = $(this).parents('.input-group').find('.goods-count')
			var addCount = new ShoppingCarObserver(goodsInput, true);
			var id="#span"+$(this).val().trim();
			$.ajax({
				url:"/admin/addCarnum",
				data:{"id":$(this).val().trim(),"goods_num":parseInt($(id).html())+1},
				success:function (data) {
					if(data.code==200){
						addCount.showCount()
						addCount.computeGoodsMoney()
					}else{
						swal("提示信息","添加到购物车失败,请检查下登录状态","error");
					}
				}
			});
		})
		$('.deleteSure').on('click', function() {
			if(deleteGoods !== null) {
				deleteGoods.deleteGoods()
			}
			$('#deleteItemTip').modal('hide')
		})
		$('#deleteMulty').on('click', function() {
			var ids='';
			var flag=false;
			$('input.goods-list-item:checked').each(function (index,item) {
				ids+=$(item).val().trim()+";";
				flag=true;
			});
			if(!flag)
				layer.open({
					title: '提示信息'
					, content: '请先选中商品',
					btn: ["确定"],
				});
			else
				mutiDel(ids);
		})
		$('.deleteMultySure').on('click', function() {



		});





	})
}

function sub() {
	if($(".submitData").hasClass("submitDis"))
		return;
	$("#add_addr_laber").hide();
	$("#add_addr").hide();
	$("#bu").click();
}

function ShoppingCarObserver(elInput, isAdd) {
	this.elInput = elInput
	this.parents = this.elInput.parents('div.goods-item')
	this.count = parseInt(this.elInput.html());
	this.isAdd = isAdd
	this.singlePrice = parseFloat(this.parents.find('.single-price').text())
	this.computeGoodsMoney = function() {
		var moneyCount = this.count * this.singlePrice
		var singleTotalEl = this.parents.find('.single-total')
		console.log(moneyCount)
		singleTotalEl.empty().append(moneyCount)
	}
	this.showCount = function() {
		var isChecked = this.parents.find('input.goods-list-item')[0].checked
		var GoodsTotalMoney = parseFloat($('#selectGoodsMoney').text())
		var goodsTotalCount = parseInt($('#selectGoodsCount').text())
		if(this.elInput) {
			if(this.isAdd) {
				///
				++this.count
				if(isChecked) {
					$('#selectGoodsMoney').empty().append(GoodsTotalMoney + this.singlePrice)
					$('#selectGoodsCount').empty().append(goodsTotalCount + 1)
				}
			} else {
				if(parseInt(this.count) <= 0) {
					$(this).addClass("disabled");
					return
				} else {
					if($(this).hasClass("disabled"))
						$(this).removeClass("disabled");
					--this.count
					if(isChecked) {
						$('#selectGoodsMoney').empty().append(GoodsTotalMoney - this.singlePrice)
						$('#selectGoodsCount').empty().append(goodsTotalCount - 1)
					}
				}
			}
			this.elInput.html(this.count)
		}
	}
	this.checkIsAll = function() {
		var checkLen = $('input.goods-list-item:checked').length
		if (checkLen > 0) {
			$('.submitData').removeClass('submitDis')
		} else {
			$('.submitData').addClass('submitDis')
		}
		if($('div.goods-item').length === checkLen) {
			$('#checked-all-bottom, #check-goods-all').prop('checked', true)
		} else {
			$('#checked-all-bottom, #check-goods-all').prop('checked', false)
		}
	}
	this.checkedChange = function(isChecked) {
		if(isChecked === undefined) {
			var isChecked = this.parents.find('input.goods-list-item')[0].checked
		}
		var itemTotalMoney = parseFloat(this.parents.find('.single-total').text())
		var GoodsTotalMoney = parseFloat($('#selectGoodsMoney').text())
		var itemCount = parseInt(this.parents.find('span.goods-count').html());
		var goodsTotalCount = parseInt($('#selectGoodsCount').text())
		if(isChecked) {
			$('#selectGoodsMoney').empty().append(itemTotalMoney + GoodsTotalMoney)
			$('#selectGoodsCount').empty().append(itemCount + goodsTotalCount)

		} else {
			if (GoodsTotalMoney - itemTotalMoney <= 0) {
				$('#selectGoodsMoney').empty().append('0.00')
				if (!$('.submitData').hasClass('submitDis')) {
					$('.submitData').addClass('submitDis')
				}
			} else {
				$('#selectGoodsMoney').empty().append(GoodsTotalMoney - itemTotalMoney)
			}
			$('#selectGoodsCount').empty().append(goodsTotalCount - itemCount)
		}
	}
	this.deleteGoods = function() {
		var isChecked = this.parents.find('input.goods-list-item')[0].checked
		if(isChecked) {
			this.checkedChange(false)
		}
		this.parents.remove()
		this.checkOptions()
	}
	this.checkOptions = function() {
		if ($('#check-goods-all')[0].checked) {
			if ($('input.goods-list-item').length <= 0) {
				$('#checked-all-bottom, #check-goods-all').prop('checked', false)
			}
		}
	}
}

var is_once=false;
var shut_down=false;

function checkedAll(_this) {
	if ($('div.goods-item').length <= 0) {
		$('.submitData').addClass('submitDis')
	} else {
		$('.submitData').removeClass('submitDis')
	}
	for(var i = 0; i < $('div.goods-item').length; i++) {
		var elInput = $('div.goods-item').eq(i).find('input.goods-list-item')
		var isChecked = $('div.goods-item').eq(i).find('input.goods-list-item')[0].checked
		var checkAllEvent = new ShoppingCarObserver(elInput, null)
		if(_this.checked) {
			if(!isChecked) {
				elInput.prop('checked', true)
				checkAllEvent.checkedChange(true)
			}
		} else {
			if (!$('.submitData').hasClass('submitDis')){
				$('.submitData').addClass('submitDis')
			}
			if(isChecked) {
				elInput.prop('checked', false)
				checkAllEvent.checkedChange(false)
			}
		}
	}
}

function alternum(id,num) {

}

function mutiDel(ids) {
	layer.open({
		title: '提示信息'
		,content: '是否删除选中的商品',
		btn:["确定","取消"],
		btn1:function (index,layero) {
			$.ajax({
				url:'/admin/deletecar',
				data:{"id":ids},
				success:function (data) {
					if(data.code==200){
						window.location.reload();
					}else{
						swal("提示信息","状态失效,请检查登录","error");
					}

				}

			});
		}
	});
}
function deleteGoodsById(car_id) {

	var  id="#"+car_id;
	if(!$(id).is(":checked")) {
		layer.open({
			title: '提示信息'
			, content: '请先选中该商品',
			btn: ["确定"],
		});
		return;
	}
	layer.open({
		title: '提示信息'
		,content: '是否删除该商品',
		btn:["确定","取消"],
		btn1:function (index,layero) {
			$.ajax({
				url:'/admin/deletecar',
				data:{"id":car_id},
				success:function (data) {
					if(data.code==200){
						window.location.reload();
					}else{
						swal("提示信息","状态失效,请检查登录","error");
					}

				}

			});
		}
	});
}
function getAddrs() {
	$.ajax({
		url:"/user/getAddrs",
		success:function (data) {
			if(data.code==200){
				var html='';
				if(data.data.length==0)
					html+='<option value="">========选择地址=======</option>';
				for(var index=0;index<data.data.length;index++){
					html+='<option value="'+data.data[index].id+'">'+data.data[index].addr+'</option>';
				}
				html+='<option value="add"><------添加新地址--------></option>';
				$("#addr").empty();
				$("#addr").html(html);
			}
		}
	});
}
function add_addr() {
	layer.open({
		title:"添加新地址",
		content:"是否添加新地址",
		btn:["确定","取消"],
		btn1:function (index,layero) {
			if(pro==""||pro==undefined) {
				layer.open({
					title: "提示信息",
					content: "省份未选择"
				});
				return;
			}
			if(city==""||city==undefined) {
				layer.open({
					title: "提示信息",
					content: "城市未选择"
				});
				return;
			}
			if(dis==""||dis==undefined) {
				layer.open({
					title: "提示信息",
					content: "区划未选择"
				});
				return;
			}
			var content=undefined;
			if($("#desc").val()==""||$("#desc").val()==undefined)
				content="xxxx";
			else
				content=$("#desc").val();
			$.ajax({
				url:"/user/addAddr",
				data:{"addr":(pro+'-'+city+'-'+dis+'-'+content)},
				success:function (data) {
					if(data.code==200)
						getAddrs();
                    layer.close(index);
                    $("#add_addr_laber").hide();
                    $("#add_addr").hide();

				}

			});
		}
	});
}

function pay() {
    var addr=$("#addr").val();
    if(addr=="") {
        layer.msg({content: "请先选择一个地址"});
        return;
    }
    var all_count=parseFloat($("#selectGoodsMoney").html());
    var strs=$("input.goods-list-item:checked");
    var ids='';
    for(var index=0;index<strs.length;index++)
        ids+=$(strs[index]).val()+";";
    $.ajax({
        url:"/user/addOrder",
        data:{"ids":ids,"addr_id":addr,"all_count":all_count},
        success:function (data) {
            if(data.code==200) {
				layer.msg("下单成功");
				window.location.href="/user/getOrderList.html";
			}
            else
                layer.msg(data.msg);
        }
    });
}


var pro=undefined;
var city=undefined;
var dis=undefined;


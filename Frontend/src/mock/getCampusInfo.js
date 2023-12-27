let userNumber = {
    'totalNumber|400-500':13
};
let placeUserNumber={
    // 1   大门
    // 2  地动山摇
    // 3  双峰埃及
    // 4  乘风破浪
    // 5  跳楼机
    // 6  欢乐小寨
    // 7  恐龙危机
    // 8  音乐餐厅
    // 9  梦幻城堡
    // 10  唐古拉雪山
    // 11  暴风眼
    // 12  飞跃极限
    // 13  玩偶对对碰
    // 14  中央水池
    // 15  天旋地转
    // 16  星际飞碟
    
    data:[{
        "attrTitle": "大门",
        "num|500-800": 33
    }, {
        "attrTitle": "地动山摇",
        "num|10-100": 1
    }, {
        "attrTitle": "双峰埃及",
        "num|10-100": 2
    }, {
        "attrTitle": "跳楼机",
        "num|10-100": 3
    }, {
        "attrTitle": "欢乐小寨",
        "num|10-100": 1
    }, {
        "attrTitle": "恐龙危机",
        "num|10-100": 1
    }, {
        "attrTitle": "音乐餐厅",
        "num|10-100": 1
    }, {
        "attrTitle": "梦幻城堡",
        "num|10-100": 2
    }, {
        "attrTitle": "唐古拉雪山",
        "num|10-100": 2
    }, {
        "attrTitle": "暴风眼",
        "num|10-100": 2
    }, {
        "attrTitle": "飞跃极限",
        "num|10-100": 2
    }, {
        "attrTitle": "玩偶对对碰",
        "num|10-100": 2
    }, {
        "attrTitle": "中央水池",
        "num|10-300": 2
    }, {
        "attrTitle": "天旋地转",
        "num|10-100": 2
    }, {
        "attrTitle": "星际飞碟",
        "num|10-100": 3
    }]
   
};
export default {
    'get|http://127.0.0.1:8181/user/getAllUsersNumber': userNumber,
    'get|http://127.0.0.1:8181/user/getPlacesUsersNumber':placeUserNumber
}


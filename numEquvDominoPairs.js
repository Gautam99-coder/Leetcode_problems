//numEquvDominoPairs
var numEquvDominoPairs = function(dominoes) {
    let count =0;
    let map = new Map(); //map to store the pairs
    for(let i=0; i<dominoes.length; i++){
        let a = dominoes[i][0];//a=[1,2]
        let b = dominoes[i][1];//b=[2,1]
        if(a>b){
            let temp=a;//temp=1
            a=b;//a=2
            b=temp;//b=1
        }
        let key = a + "," + b; //now a=1, b=2, key=1,2
        if(map.has(key)){ //key=1,2
            count += map.get(key); //count=0, map.get(key)=1
            map.set(key, map.get(key)+ 1);  //key=1,2 map.get(key)=1, set the value to 2
        }
        else{
            map.set(key, 1); //key=1,2 if it doesn't exist, set the value to 1
        }
    }
    return count;
}
console.log(numEquvDominoPairs([[1,2],[2,1],[3,4],[5,6]])); // 1

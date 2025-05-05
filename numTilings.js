/**
 * @param {number} n
 * @return {number}
 */
var numTilings = function(n) {
    const mod = 1e9 + 7;
    // Create an array dp of length n+1. dp[i] represents the number of tilings for a 2 x i board.
    let dp = new Array(n + 1).fill(0);
    
    // Base cases:
    dp[0] = 1;  // Convention: empty board has 1 tiling.
    if (n >= 1) dp[1] = 1;  // 2 x 1 board: only 1 vertical domino.
    if (n >= 2) dp[2] = 2;  // 2 x 2 board: either two vertical or two horizontal dominoes.
    
    // Fill dp table using the recurrence for n >= 3.
    for (let i = 3; i <= n; i++) {
        dp[i] = (2 * dp[i - 1] + dp[i - 3]) % mod;
    }
    
    return dp[n];
};

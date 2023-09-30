## 프로그래머스 전화번호 목록
```java
import java.util.*;
class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Arrays.sort(phone_book);
        for(int i=0; i<phone_book.length-1; i++) {
            if (phone_book[i+1].startsWith(phone_book[i])) {
                return false;
            }
        }
        return true;
    }
}
```
처음에 이렇게 접근하였는데 이 문제는 트라이를 통해서 문제를 풀 수 있습니다.

## 트라이란?
트라이(Trie)는 문자열 집합을 표현하는 트리 자료구조입니다. 트라이는 검색트리의 일종으로, 문자열 검색에 효율적입니다. 특히, 이 문제에서는 효율적이다. 문자열의 집합이 주어졌을 때, 트라이를 사용하면 주어진 문자열이 집합에 속하는지 빠르게 검색할 수 있는것이 장점입니다.

### **트라이의 특징:**

1. **효율적인 문자열 검색**: 트라이를 사용하면 문자열을 빠르게 검색할 수 있습니다. 문자열의 길이를 *n*이라 할 때, 시간 복잡도는 *O*(*n*)입니다. (최고)
2. **공간 효율성**: 비슷한 접두사를 가진 문자열들을 공유할 수 있어 공간을 효율적으로 사용할 수 있습니다.

### **트라이의 예시:**

문제에서 제공한 문자열 집합 ["car", "card", "cat", "bat"]를 저장하는 트라이를 그려보면 아래와 같습니다. 

```css
cssCopy code
        root
       /    \
      c      b
     / \      \
    a   a      a
   /     \      \
  r       t      t
 /
d

```



### **트라이의 사용 사례:**

- 자동 완성 기능: 사용자가 입력한 접두사를 기반으로 가능한 모든 단어를 빠르게 찾을 수 있습니다. 위 트라이 기능을 이용하면 전화번호부 문제를 아래와 같이 재구성할 수 있습니다. 

```java
class Solution {
    Solution[] child;
    boolean end;
    
    public Solution() {
        child = new Solution[10];
        end = false;
    }
    
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Solution root = new Solution();
        for (String phoneNumber : phone_book) {
            if(!root.insert(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
    
    boolean insert(String phoneNumber) {
        Solution current = this;
        for(char ch : phoneNumber.toCharArray()) {
            int index = ch - '0';
       
            if (current.child[index] == null) {
                current.child[index] = new Solution();
            }
            current = current.child[index];
            if (current.end) {
                return false;
            }
        }
        current.end = true;
        for (Solution solution : current.child) {
            if (solution != null) return false;
        }
        return true;
    }
}
```

굿 새로운것을 배우는 느낌이라 아주 재밌네요!
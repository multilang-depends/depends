typedef Complex<int64_t> ComplexInteger;
// 简单 typedef
typedef unsigned long ulong;
 
// 下列二个对象拥有同一类型
unsigned long l1;
ulong l2;
 
// 更复杂的 typedef
// typedef int int_t, *intp_t, (&fp)(int, ulong), arr_t[10];
 
// 下列二个对象拥有同一类型
int a1[10];
arr_t a2;
 
// 避免必须写 "struct C" 的常见 C 手法
typedef struct {int a; int b;} S;
 
// 下列二个对象拥有相同类型
pS ps1;
S* ps2;
 
// 错误：存储类指定符不能出现于 typedef 声明
// typedef static unsigned int uint;
 
// typedef 可用在 decl-specifier-seq 中的任何位置
long unsigned typedef int long ullong;
// 写作 "typedef unsigned long long int ullong;" 更符合惯例
 
// std::add_const ，类似其他元函数，使用成员 typedef
template< class T>
struct add_const {
    typedef const T type;
};
 
typedef struct Node {
    struct listNode* next; // 声明名为 listNode 的新的（不完整）结构体类型
} listNode; // 错误：与先前声明的结构体名冲突




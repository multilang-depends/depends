
class F {
    void foo();
};
using aliasName = F;

void bar(){
    aliasName::foo();
}

using all_true = std::is_same<bool_pack<true, v...>, bool_pack<v..., true>>;



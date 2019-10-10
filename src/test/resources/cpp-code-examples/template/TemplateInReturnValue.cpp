namespace std {
class tuple_element<T1,T2>{
    class type{

    };
};
}

template <size_t Index>
const typename std::tuple_element<Index, std::tuple<Domains...>>::type& get()
  const {
return std::get<Index>(m_product);
}

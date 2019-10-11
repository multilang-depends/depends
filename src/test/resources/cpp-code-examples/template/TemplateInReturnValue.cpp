namespace std {
class tuple_element<T1,T2>{
    class type{

    };

};
class tuple{
};
}

class Index{
};

class Domains{
};

template <size_t Index>
const typename std::tuple_element<Index, std::tuple<Domains...>,Index >::type& get()
  const {
return std::get<Index>(m_product);
}

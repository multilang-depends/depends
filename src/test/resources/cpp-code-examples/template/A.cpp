


template <size_t Index>
const typename std::tuple_element<Index, std::tuple<Domains...>>::type& get()
  const {
return std::get<Index>(m_product);
}



/** typename with dots */
template <typename ...T>
  match_t<DexMethod, std::tuple<std::tuple<T...> > > has_opcodes(const std::tuple<T...>& t) {
  return {
    [](const DexMethod* meth, const std::tuple<T...>& t) {
      auto code = meth->get_code();
      if (code) {
        const size_t N = std::tuple_size<std::tuple<T...> >::value;
        std::vector<IRInstruction*> insns;
        for (auto& mie : InstructionIterable(code)) {
          insns.push_back(mie.insn);
        }
        // No way to match if we have less insns than N
        if (insns.size() < N) {
          return false;
        }
        // Try to match starting at i, we advance along insns until the length of the tuple
        // would cause us to extend beyond the end of insns to make the match.
        for (size_t i = 0 ; i <= insns.size() - N ; ++i) {
          if (insns_matcher<std::tuple<T...>, std::integral_constant<size_t, 0> >::matches_at(i, insns, t)) {
            return true;
          }
        }
      }
      return false;
    },
    t
  };
}

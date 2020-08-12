static void register_hooks(apr_pool_t *p)
{
}

typedef struct module_struct module;
struct module_struct {
    void (*register_hooks) (apr_pool_t *p);
};

module bar = {register_hooks};

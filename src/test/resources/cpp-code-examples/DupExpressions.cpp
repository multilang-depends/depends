typedef struct request_rec request_rec;

struct request_rec {

};

void foo(request_rec* r){
    if (r->uri[0] != '/') {
        return DECLINED;
    }
}
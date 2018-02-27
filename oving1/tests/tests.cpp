#include <cassert>
#include "../is_prime.cpp"

int main() {
    assert(is_prime(3));
    assert(!is_prime(12));
    assert(is_prime(101));
}

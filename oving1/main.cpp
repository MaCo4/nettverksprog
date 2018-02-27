#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include "is_prime.cpp"

using namespace std;
using namespace chrono;



int main() {

    vector<thread> threads;
    vector<int> primes;
    mutex primes_mutex;

    int threads_to_use = 8;
    int min_prime = 0;
    int max_prime = 20000000;

    cout << "Starter nå" << endl;
    high_resolution_clock::time_point start = high_resolution_clock::now();

    for (int i = 0; i < threads_to_use; ++i) {
        threads.emplace_back([&primes, &primes_mutex, &min_prime, &max_prime, &threads_to_use, i] () {
            for (int j = min_prime + i; j <= max_prime; j += threads_to_use) {
                if (is_prime(j)) {
                    primes_mutex.lock();
                    primes.emplace_back(j);
                    primes_mutex.unlock();
                }
            }
        });
    }

    for (auto &thread : threads) {
        thread.join();
    }
    high_resolution_clock::time_point end = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(end - start).count();

    //sort(primes.begin(), primes.end());
    cout << "Antall primtall: " << primes.size() << endl;
    cout << "Tid brukt: " << duration << endl;

    /*for (auto &prime : primes) {
        cout << prime << " ";
    }*/
    
    cout << endl;
    return 0;
}

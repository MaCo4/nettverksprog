cmake_minimum_required(VERSION 3.9)
project(Nettverksprog)

set(CMAKE_CXX_STANDARD 11)

add_executable(Nettverksprog oving1/main.cpp)

enable_testing()
add_subdirectory(oving1/tests)

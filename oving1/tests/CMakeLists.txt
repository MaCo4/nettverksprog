include_directories(${CMAKE_SOURCE_DIR})

add_executable(tests tests.cpp)
add_test(tests tests)

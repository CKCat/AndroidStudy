# include <iostream>
using namespace std;

//依赖关系(虚线)：一个类是另一个类的函数参数或返回值
// 关联(实线)：一个类是另一个类的成员。
// 聚合(菱形夹实线)：整体和部分的关系(车和发动机的关系,发动机可以是不同厂商的)。
// 组合(实心的菱形加实线)：生命体整体和部分的关系(人和手脚的关系)。
class Car
{
public:
protected:
private:
};

class ZhangSan
{
public:
    Car* getCar()
    {

    }

    void getCar(Car* car)
    {
        
    }
protected:
private:
};


int mian()
{
    cout << "Hello World!"<<endl;
    return 0;
}
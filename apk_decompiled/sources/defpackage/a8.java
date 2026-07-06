package defpackage;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a8 extends y {
    public static final Object[] h = new Object[0];
    public int e;
    public Object[] f = h;
    public int g;

    @Override // defpackage.y
    public final int a() {
        return this.g;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2;
        int i3 = this.g;
        if (i >= 0 && i <= i3) {
            if (i == i3) {
                addLast(obj);
                return;
            }
            if (i == 0) {
                addFirst(obj);
                return;
            }
            j();
            d(this.g + 1);
            int i4 = i(this.e + i);
            int i5 = this.g;
            if (i < ((i5 + 1) >> 1)) {
                if (i4 == 0) {
                    Object[] objArr = this.f;
                    objArr.getClass();
                    i2 = objArr.length - 1;
                } else {
                    i2 = i4 - 1;
                }
                int i6 = this.e;
                if (i6 == 0) {
                    Object[] objArr2 = this.f;
                    objArr2.getClass();
                    i6 = objArr2.length;
                }
                int i7 = i6 - 1;
                int i8 = this.e;
                Object[] objArr3 = this.f;
                if (i2 >= i8) {
                    objArr3[i7] = objArr3[i8];
                    i8.N(objArr3, objArr3, i8, i8 + 1, i2 + 1);
                } else {
                    i8.N(objArr3, objArr3, i8 - 1, i8, objArr3.length);
                    Object[] objArr4 = this.f;
                    objArr4[objArr4.length - 1] = objArr4[0];
                    i8.N(objArr4, objArr4, 0, 1, i2 + 1);
                }
                this.f[i2] = obj;
                this.e = i7;
            } else {
                int i9 = i(i5 + this.e);
                Object[] objArr5 = this.f;
                if (i4 < i9) {
                    i8.N(objArr5, objArr5, i4 + 1, i4, i9);
                } else {
                    i8.N(objArr5, objArr5, 1, 0, i9);
                    Object[] objArr6 = this.f;
                    objArr6[0] = objArr6[objArr6.length - 1];
                    i8.N(objArr6, objArr6, i4 + 1, i4, objArr6.length - 1);
                }
                this.f[i4] = obj;
            }
            this.g++;
            return;
        }
        v7.f(d3.u("index: ", i, ", size: ", i3));
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        collection.getClass();
        int i2 = this.g;
        if (i >= 0 && i <= i2) {
            if (collection.isEmpty()) {
                return false;
            }
            if (i == this.g) {
                return addAll(collection);
            }
            j();
            d(collection.size() + this.g);
            int i3 = i(this.g + this.e);
            int i4 = i(this.e + i);
            int size = collection.size();
            if (i < ((this.g + 1) >> 1)) {
                int i5 = this.e;
                int i6 = i5 - size;
                Object[] objArr = this.f;
                if (i4 >= i5) {
                    if (i6 >= 0) {
                        i8.N(objArr, objArr, i6, i5, i4);
                    } else {
                        i6 += objArr.length;
                        int i7 = i4 - i5;
                        int length = objArr.length - i6;
                        if (length >= i7) {
                            i8.N(objArr, objArr, i6, i5, i4);
                        } else {
                            i8.N(objArr, objArr, i6, i5, i5 + length);
                            Object[] objArr2 = this.f;
                            i8.N(objArr2, objArr2, 0, this.e + length, i4);
                        }
                    }
                } else {
                    i8.N(objArr, objArr, i6, i5, objArr.length);
                    Object[] objArr3 = this.f;
                    if (size >= i4) {
                        i8.N(objArr3, objArr3, objArr3.length - size, 0, i4);
                    } else {
                        i8.N(objArr3, objArr3, objArr3.length - size, 0, size);
                        Object[] objArr4 = this.f;
                        i8.N(objArr4, objArr4, 0, size, i4);
                    }
                }
                this.e = i6;
                c(g(i4 - size), collection);
                return true;
            }
            int i8 = i4 + size;
            Object[] objArr5 = this.f;
            if (i4 < i3) {
                int i9 = size + i3;
                if (i9 <= objArr5.length) {
                    i8.N(objArr5, objArr5, i8, i4, i3);
                } else if (i8 >= objArr5.length) {
                    i8.N(objArr5, objArr5, i8 - objArr5.length, i4, i3);
                } else {
                    int length2 = i3 - (i9 - objArr5.length);
                    i8.N(objArr5, objArr5, 0, length2, i3);
                    Object[] objArr6 = this.f;
                    i8.N(objArr6, objArr6, i8, i4, length2);
                }
            } else {
                i8.N(objArr5, objArr5, size, 0, i3);
                Object[] objArr7 = this.f;
                if (i8 >= objArr7.length) {
                    i8.N(objArr7, objArr7, i8 - objArr7.length, i4, objArr7.length);
                } else {
                    i8.N(objArr7, objArr7, 0, objArr7.length - size, objArr7.length);
                    Object[] objArr8 = this.f;
                    i8.N(objArr8, objArr8, i8, i4, objArr8.length - size);
                }
            }
            c(i4, collection);
            return true;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return false;
    }

    public final void addFirst(Object obj) {
        j();
        d(this.g + 1);
        int i = this.e;
        if (i == 0) {
            Object[] objArr = this.f;
            objArr.getClass();
            i = objArr.length;
        }
        int i2 = i - 1;
        this.e = i2;
        this.f[i2] = obj;
        this.g++;
    }

    public final void addLast(Object obj) {
        j();
        d(a() + 1);
        this.f[i(a() + this.e)] = obj;
        this.g = a() + 1;
    }

    @Override // defpackage.y
    public final Object b(int i) {
        int i2 = this.g;
        if (i >= 0 && i < i2) {
            if (i == a() - 1) {
                return removeLast();
            }
            if (i == 0) {
                return removeFirst();
            }
            j();
            int i3 = i(this.e + i);
            Object[] objArr = this.f;
            Object obj = objArr[i3];
            int i4 = this.g >> 1;
            int i5 = this.e;
            if (i < i4) {
                if (i3 >= i5) {
                    i8.N(objArr, objArr, i5 + 1, i5, i3);
                } else {
                    i8.N(objArr, objArr, 1, 0, i3);
                    Object[] objArr2 = this.f;
                    objArr2[0] = objArr2[objArr2.length - 1];
                    int i6 = this.e;
                    i8.N(objArr2, objArr2, i6 + 1, i6, objArr2.length - 1);
                }
                Object[] objArr3 = this.f;
                int i7 = this.e;
                objArr3[i7] = null;
                this.e = e(i7);
            } else {
                int i8 = i((a() - 1) + i5);
                Object[] objArr4 = this.f;
                if (i3 <= i8) {
                    i8.N(objArr4, objArr4, i3, i3 + 1, i8 + 1);
                } else {
                    i8.N(objArr4, objArr4, i3, i3 + 1, objArr4.length);
                    Object[] objArr5 = this.f;
                    objArr5[objArr5.length - 1] = objArr5[0];
                    i8.N(objArr5, objArr5, 0, 1, i8 + 1);
                }
                this.f[i8] = null;
            }
            this.g--;
            return obj;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    public final void c(int i, Collection collection) {
        Iterator it = collection.iterator();
        int length = this.f.length;
        while (i < length && it.hasNext()) {
            this.f[i] = it.next();
            i++;
        }
        int i2 = this.e;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.f[i3] = it.next();
        }
        this.g = collection.size() + this.g;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        if (!isEmpty()) {
            j();
            h(this.e, i(a() + this.e));
        }
        this.e = 0;
        this.g = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        if (indexOf(obj) != -1) {
            return true;
        }
        return false;
    }

    public final void d(int i) {
        if (i >= 0) {
            Object[] objArr = this.f;
            if (i <= objArr.length) {
                return;
            }
            if (objArr == h) {
                if (i < 10) {
                    i = 10;
                }
                this.f = new Object[i];
                return;
            }
            int length = objArr.length;
            int i2 = length + (length >> 1);
            if (i2 - i < 0) {
                i2 = i;
            }
            if (i2 - 2147483639 > 0) {
                if (i > 2147483639) {
                    i2 = Integer.MAX_VALUE;
                } else {
                    i2 = 2147483639;
                }
            }
            Object[] objArr2 = new Object[i2];
            i8.N(objArr, objArr2, 0, this.e, objArr.length);
            Object[] objArr3 = this.f;
            int length2 = objArr3.length;
            int i3 = this.e;
            i8.N(objArr3, objArr2, length2 - i3, 0, i3);
            this.e = 0;
            this.f = objArr2;
            return;
        }
        v7.o("Deque is too big.");
    }

    public final int e(int i) {
        this.f.getClass();
        if (i == r0.length - 1) {
            return 0;
        }
        return i + 1;
    }

    public final Object f() {
        if (isEmpty()) {
            return null;
        }
        return this.f[i((size() - 1) + this.e)];
    }

    public final int g(int i) {
        if (i < 0) {
            return i + this.f.length;
        }
        return i;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        int a = a();
        if (i >= 0 && i < a) {
            return this.f[i(this.e + i)];
        }
        v7.f(d3.u("index: ", i, ", size: ", a));
        return null;
    }

    public final void h(int i, int i2) {
        Object[] objArr = this.f;
        if (i < i2) {
            i8.R(objArr, i, i2);
        } else {
            Arrays.fill(objArr, i, objArr.length, (Object) null);
            i8.R(this.f, 0, i2);
        }
    }

    public final int i(int i) {
        Object[] objArr = this.f;
        if (i >= objArr.length) {
            return i - objArr.length;
        }
        return i;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int i;
        int i2 = i(a() + this.e);
        int i3 = this.e;
        if (i3 < i2) {
            while (i3 < i2) {
                if (o20.e(obj, this.f[i3])) {
                    i = this.e;
                } else {
                    i3++;
                }
            }
            return -1;
        }
        if (!isEmpty() && (i3 = this.e) >= i2) {
            int length = this.f.length;
            while (true) {
                if (i3 < length) {
                    if (o20.e(obj, this.f[i3])) {
                        i = this.e;
                        break;
                    }
                    i3++;
                } else {
                    for (int i4 = 0; i4 < i2; i4++) {
                        if (o20.e(obj, this.f[i4])) {
                            i3 = i4 + this.f.length;
                            i = this.e;
                        }
                    }
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return i3 - i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        if (a() == 0) {
            return true;
        }
        return false;
    }

    public final void j() {
        ((AbstractList) this).modCount++;
    }

    public final Object last() {
        if (!isEmpty()) {
            return this.f[i((size() - 1) + this.e)];
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        int length;
        int i;
        int i2 = i(this.g + this.e);
        int i3 = this.e;
        if (i3 < i2) {
            length = i2 - 1;
            if (i3 <= length) {
                while (!o20.e(obj, this.f[length])) {
                    if (length != i3) {
                        length--;
                    }
                }
                i = this.e;
                return length - i;
            }
            return -1;
        }
        if (!isEmpty() && this.e >= i2) {
            while (true) {
                i2--;
                Object[] objArr = this.f;
                if (-1 < i2) {
                    if (o20.e(obj, objArr[i2])) {
                        length = i2 + this.f.length;
                        i = this.e;
                        break;
                    }
                } else {
                    objArr.getClass();
                    length = objArr.length - 1;
                    int i4 = this.e;
                    if (i4 <= length) {
                        while (!o20.e(obj, this.f[length])) {
                            if (length != i4) {
                                length--;
                            }
                        }
                        i = this.e;
                    }
                }
            }
            return length - i;
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        b(indexOf);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int i;
        Object[] objArr;
        collection.getClass();
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.f.length != 0) {
            int i2 = i(this.g + this.e);
            int i3 = this.e;
            if (i3 < i2) {
                i = i3;
                while (true) {
                    objArr = this.f;
                    if (i3 >= i2) {
                        break;
                    }
                    Object obj = objArr[i3];
                    if (!collection.contains(obj)) {
                        this.f[i] = obj;
                        i++;
                    } else {
                        z = true;
                    }
                    i3++;
                }
                i8.R(objArr, i, i2);
            } else {
                int length = this.f.length;
                boolean z2 = false;
                int i4 = i3;
                while (i3 < length) {
                    Object[] objArr2 = this.f;
                    Object obj2 = objArr2[i3];
                    objArr2[i3] = null;
                    if (!collection.contains(obj2)) {
                        this.f[i4] = obj2;
                        i4++;
                    } else {
                        z2 = true;
                    }
                    i3++;
                }
                i = i(i4);
                for (int i5 = 0; i5 < i2; i5++) {
                    Object[] objArr3 = this.f;
                    Object obj3 = objArr3[i5];
                    objArr3[i5] = null;
                    if (!collection.contains(obj3)) {
                        this.f[i] = obj3;
                        i = e(i);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                j();
                this.g = g(i - this.e);
            }
        }
        return z;
    }

    public final Object removeFirst() {
        if (!isEmpty()) {
            j();
            Object[] objArr = this.f;
            int i = this.e;
            Object obj = objArr[i];
            objArr[i] = null;
            this.e = e(i);
            this.g = a() - 1;
            return obj;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    public final Object removeLast() {
        if (!isEmpty()) {
            j();
            int i = i((size() - 1) + this.e);
            Object[] objArr = this.f;
            Object obj = objArr[i];
            objArr[i] = null;
            this.g = a() - 1;
            return obj;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Override // java.util.AbstractList
    public final void removeRange(int i, int i2) {
        k81.n(i, i2, this.g);
        int i3 = i2 - i;
        if (i3 == 0) {
            return;
        }
        if (i3 == this.g) {
            clear();
            return;
        }
        if (i3 == 1) {
            b(i);
            return;
        }
        j();
        int i4 = this.g - i2;
        int i5 = this.e;
        if (i < i4) {
            int i6 = i((i - 1) + i5);
            int i7 = i(this.e + (i2 - 1));
            while (i > 0) {
                int i8 = i6 + 1;
                int min = Math.min(i, Math.min(i8, i7 + 1));
                Object[] objArr = this.f;
                int i9 = i7 - min;
                int i10 = i6 - min;
                i8.N(objArr, objArr, i9 + 1, i10 + 1, i8);
                i6 = g(i10);
                i7 = g(i9);
                i -= min;
            }
            int i11 = i(this.e + i3);
            h(this.e, i11);
            this.e = i11;
        } else {
            int i12 = i(i5 + i2);
            int i13 = i(this.e + i);
            int i14 = this.g;
            while (true) {
                i14 -= i2;
                if (i14 <= 0) {
                    break;
                }
                Object[] objArr2 = this.f;
                i2 = Math.min(i14, Math.min(objArr2.length - i12, objArr2.length - i13));
                Object[] objArr3 = this.f;
                int i15 = i12 + i2;
                i8.N(objArr3, objArr3, i13, i12, i15);
                i12 = i(i15);
                i13 = i(i13 + i2);
            }
            int i16 = i(this.g + this.e);
            h(g(i16 - i3), i16);
        }
        this.g -= i3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        int i;
        Object[] objArr;
        collection.getClass();
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.f.length != 0) {
            int i2 = i(this.g + this.e);
            int i3 = this.e;
            if (i3 < i2) {
                i = i3;
                while (true) {
                    objArr = this.f;
                    if (i3 >= i2) {
                        break;
                    }
                    Object obj = objArr[i3];
                    if (collection.contains(obj)) {
                        this.f[i] = obj;
                        i++;
                    } else {
                        z = true;
                    }
                    i3++;
                }
                i8.R(objArr, i, i2);
            } else {
                int length = this.f.length;
                boolean z2 = false;
                int i4 = i3;
                while (i3 < length) {
                    Object[] objArr2 = this.f;
                    Object obj2 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj2)) {
                        this.f[i4] = obj2;
                        i4++;
                    } else {
                        z2 = true;
                    }
                    i3++;
                }
                i = i(i4);
                for (int i5 = 0; i5 < i2; i5++) {
                    Object[] objArr3 = this.f;
                    Object obj3 = objArr3[i5];
                    objArr3[i5] = null;
                    if (collection.contains(obj3)) {
                        this.f[i] = obj3;
                        i = e(i);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                j();
                this.g = g(i - this.e);
            }
        }
        return z;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        int a = a();
        if (i >= 0 && i < a) {
            int i2 = i(this.e + i);
            Object[] objArr = this.f;
            Object obj2 = objArr[i2];
            objArr[i2] = obj;
            return obj2;
        }
        v7.f(d3.u("index: ", i, ", size: ", a));
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        int i = this.g;
        if (length < i) {
            Object newInstance = Array.newInstance(objArr.getClass().getComponentType(), i);
            newInstance.getClass();
            objArr = (Object[]) newInstance;
        }
        int i2 = i(this.g + this.e);
        int i3 = this.e;
        if (i3 < i2) {
            i8.P(this.f, objArr, i3, i2, 2);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.f;
            i8.N(objArr2, objArr, 0, this.e, objArr2.length);
            Object[] objArr3 = this.f;
            i8.N(objArr3, objArr, objArr3.length - this.e, 0, i2);
        }
        int i4 = this.g;
        if (i4 < objArr.length) {
            objArr[i4] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[a()]);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        collection.getClass();
        if (collection.isEmpty()) {
            return false;
        }
        j();
        d(collection.size() + a());
        c(i(a() + this.e), collection);
        return true;
    }
}

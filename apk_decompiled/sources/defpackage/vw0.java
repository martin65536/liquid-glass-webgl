package defpackage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vw0 extends b0 {
    public static final vw0 f = new vw0(new Object[0]);
    public final Object[] e;

    public vw0(Object[] objArr) {
        this.e = objArr;
    }

    @Override // defpackage.m
    public final int a() {
        return this.e.length;
    }

    @Override // defpackage.b0
    public final b0 b(int i, Object obj) {
        Object[] objArr = this.e;
        m20.l(i, objArr.length);
        if (i == objArr.length) {
            return c(obj);
        }
        if (objArr.length < 32) {
            Object[] objArr2 = new Object[objArr.length + 1];
            i8.P(objArr, objArr2, 0, i, 6);
            i8.N(objArr, objArr2, i + 1, i, objArr.length);
            objArr2[i] = obj;
            return new vw0(objArr2);
        }
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
        i8.N(objArr, copyOf, i + 1, i, objArr.length - 1);
        copyOf[i] = obj;
        Object[] objArr3 = new Object[32];
        objArr3[0] = objArr[31];
        return new xl0(copyOf, objArr3, objArr.length + 1, 0);
    }

    @Override // defpackage.b0
    public final b0 c(Object obj) {
        Object[] objArr = this.e;
        if (objArr.length < 32) {
            Object[] copyOf = Arrays.copyOf(objArr, objArr.length + 1);
            copyOf[objArr.length] = obj;
            return new vw0(copyOf);
        }
        Object[] objArr2 = new Object[32];
        objArr2[0] = obj;
        return new xl0(objArr, objArr2, objArr.length + 1, 0);
    }

    @Override // defpackage.b0
    public final b0 d(Collection collection) {
        Object[] objArr = this.e;
        if (collection.size() + objArr.length <= 32) {
            Object[] copyOf = Arrays.copyOf(objArr, collection.size() + objArr.length);
            int length = objArr.length;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                copyOf[length] = it.next();
                length++;
            }
            return new vw0(copyOf);
        }
        yl0 e = e();
        e.addAll(collection);
        return e.c();
    }

    @Override // defpackage.b0
    public final yl0 e() {
        return new yl0(this, null, this.e, 0);
    }

    @Override // defpackage.b0
    public final b0 f(a0 a0Var) {
        Object[] objArr = this.e;
        int length = objArr.length;
        int length2 = objArr.length;
        Object[] objArr2 = objArr;
        boolean z = false;
        for (int i = 0; i < length2; i++) {
            Object obj = objArr[i];
            if (((Boolean) a0Var.e(obj)).booleanValue()) {
                if (!z) {
                    objArr2 = Arrays.copyOf(objArr, objArr.length);
                    z = true;
                    length = i;
                }
            } else if (z) {
                objArr2[length] = obj;
                length++;
            }
        }
        if (length == objArr.length) {
            return this;
        }
        if (length == 0) {
            return f;
        }
        return new vw0(i8.Q(objArr2, 0, length));
    }

    @Override // defpackage.b0
    public final b0 g(int i) {
        Object[] objArr = this.e;
        m20.j(i, objArr.length);
        if (objArr.length == 1) {
            return f;
        }
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length - 1);
        i8.N(objArr, copyOf, i, i + 1, objArr.length);
        return new vw0(copyOf);
    }

    @Override // java.util.List
    public final Object get(int i) {
        Object[] objArr = this.e;
        m20.j(i, objArr.length);
        return objArr[i];
    }

    @Override // defpackage.b0
    public final b0 h(int i, Object obj) {
        Object[] objArr = this.e;
        m20.j(i, objArr.length);
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
        copyOf[i] = obj;
        return new vw0(copyOf);
    }

    @Override // defpackage.w, java.util.List
    public final int indexOf(Object obj) {
        return i8.U(this.e, obj);
    }

    @Override // defpackage.w, java.util.List
    public final int lastIndexOf(Object obj) {
        Object[] objArr = this.e;
        if (obj == null) {
            int length = objArr.length - 1;
            if (length >= 0) {
                while (true) {
                    int i = length - 1;
                    if (objArr[length] == null) {
                        return length;
                    }
                    if (i < 0) {
                        break;
                    }
                    length = i;
                }
            }
        } else {
            int length2 = objArr.length - 1;
            if (length2 >= 0) {
                while (true) {
                    int i2 = length2 - 1;
                    if (obj.equals(objArr[length2])) {
                        return length2;
                    }
                    if (i2 < 0) {
                        break;
                    }
                    length2 = i2;
                }
            }
        }
        return -1;
    }

    @Override // defpackage.w, java.util.List
    public final ListIterator listIterator(int i) {
        Object[] objArr = this.e;
        m20.l(i, objArr.length);
        return new wb(objArr, i, objArr.length);
    }
}

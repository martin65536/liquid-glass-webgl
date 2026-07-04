package defpackage;

import java.util.ConcurrentModificationException;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cc0 implements Map.Entry, q30 {
    public final ec0 e;
    public final int f;
    public final int g;

    public cc0(ec0 ec0Var, int i) {
        ec0Var.getClass();
        this.e = ec0Var;
        this.f = i;
        this.g = ec0Var.l;
    }

    public final void a() {
        if (this.e.l == this.g) {
        } else {
            throw new ConcurrentModificationException("The backing map has been modified after this entry was obtained.");
        }
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            if (o20.e(entry.getKey(), getKey()) && o20.e(entry.getValue(), getValue())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        a();
        return this.e.e[this.f];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        a();
        Object[] objArr = this.e.f;
        objArr.getClass();
        return objArr[this.f];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int i;
        Object key = getKey();
        int i2 = 0;
        if (key != null) {
            i = key.hashCode();
        } else {
            i = 0;
        }
        Object value = getValue();
        if (value != null) {
            i2 = value.hashCode();
        }
        return i ^ i2;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        a();
        ec0 ec0Var = this.e;
        ec0Var.b();
        Object[] objArr = ec0Var.f;
        if (objArr == null) {
            int length = ec0Var.e.length;
            if (length >= 0) {
                objArr = new Object[length];
                ec0Var.f = objArr;
            } else {
                v7.m("capacity must be non-negative.");
                return null;
            }
        }
        int i = this.f;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey());
        sb.append('=');
        sb.append(getValue());
        return sb.toString();
    }
}

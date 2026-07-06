package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fc0 extends z {
    public final /* synthetic */ int e;
    public final ec0 f;

    public /* synthetic */ fc0(ec0 ec0Var, int i) {
        this.e = i;
        this.f = ec0Var;
    }

    @Override // defpackage.z
    public final int a() {
        switch (this.e) {
            case 0:
                return this.f.m;
            default:
                return this.f.m;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        switch (this.e) {
            case 0:
                ((Map.Entry) obj).getClass();
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        int i = this.e;
        collection.getClass();
        switch (i) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        switch (this.e) {
            case 0:
                this.f.clear();
                return;
            default:
                this.f.clear();
                return;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        int i = this.e;
        ec0 ec0Var = this.f;
        switch (i) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                ec0Var.getClass();
                int f = ec0Var.f(entry.getKey());
                if (f < 0) {
                    return false;
                }
                Object[] objArr = ec0Var.f;
                objArr.getClass();
                return o20.e(objArr[f], entry.getValue());
            default:
                return ec0Var.containsKey(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection collection) {
        switch (this.e) {
            case 0:
                collection.getClass();
                return this.f.d(collection);
            default:
                return super.containsAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        switch (this.e) {
            case 0:
                return this.f.isEmpty();
            default:
                return this.f.isEmpty();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        int i = this.e;
        ec0 ec0Var = this.f;
        switch (i) {
            case 0:
                ec0Var.getClass();
                return new bc0(ec0Var, 0);
            default:
                ec0Var.getClass();
                return new bc0(ec0Var, 1);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int i = this.e;
        ec0 ec0Var = this.f;
        switch (i) {
            case 0:
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    ec0Var.getClass();
                    ec0Var.b();
                    int f = ec0Var.f(entry.getKey());
                    if (f >= 0) {
                        Object[] objArr = ec0Var.f;
                        objArr.getClass();
                        if (o20.e(objArr[f], entry.getValue())) {
                            ec0Var.j(f);
                            return true;
                        }
                    }
                }
                return false;
            default:
                ec0Var.b();
                int f2 = ec0Var.f(obj);
                if (f2 < 0) {
                    return false;
                }
                ec0Var.j(f2);
                return true;
        }
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        int i = this.e;
        ec0 ec0Var = this.f;
        collection.getClass();
        switch (i) {
            case 0:
                ec0Var.b();
                return super.removeAll(collection);
            default:
                ec0Var.b();
                return super.removeAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        int i = this.e;
        ec0 ec0Var = this.f;
        collection.getClass();
        switch (i) {
            case 0:
                ec0Var.b();
                return super.retainAll(collection);
            default:
                ec0Var.b();
                return super.retainAll(collection);
        }
    }
}

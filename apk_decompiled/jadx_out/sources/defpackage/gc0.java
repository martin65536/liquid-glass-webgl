package defpackage;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gc0 extends AbstractCollection implements Collection, q30 {
    public final /* synthetic */ int e;
    public final Object f;

    public /* synthetic */ gc0(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection collection) {
        switch (this.e) {
            case 0:
                collection.getClass();
                throw new UnsupportedOperationException();
            default:
                return super.addAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        switch (this.e) {
            case 0:
                ((ec0) this.f).clear();
                return;
            default:
                ((ol0) this.f).clear();
                return;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        switch (this.e) {
            case 0:
                return ((ec0) this.f).containsValue(obj);
            default:
                return ((ol0) this.f).containsValue(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        switch (this.e) {
            case 0:
                return ((ec0) this.f).isEmpty();
            default:
                return super.isEmpty();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                ec0 ec0Var = (ec0) obj;
                ec0Var.getClass();
                return new bc0(ec0Var, 2);
            default:
                ol0 ol0Var = (ol0) obj;
                b31[] b31VarArr = new b31[8];
                for (int i2 = 0; i2 < 8; i2++) {
                    b31VarArr[i2] = new c31(2);
                }
                return new pl0(ol0Var, b31VarArr);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        switch (this.e) {
            case 0:
                ec0 ec0Var = (ec0) this.f;
                ec0Var.b();
                int g = ec0Var.g(obj);
                if (g < 0) {
                    return false;
                }
                ec0Var.j(g);
                return true;
            default:
                return super.remove(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection collection) {
        switch (this.e) {
            case 0:
                collection.getClass();
                ((ec0) this.f).b();
                return super.removeAll(collection);
            default:
                return super.removeAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection collection) {
        switch (this.e) {
            case 0:
                collection.getClass();
                ((ec0) this.f).b();
                return super.retainAll(collection);
            default:
                return super.retainAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        switch (this.e) {
            case 0:
                return ((ec0) this.f).m;
            default:
                return ((ol0) this.f).i;
        }
    }
}

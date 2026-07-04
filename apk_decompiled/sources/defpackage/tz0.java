package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tz0 extends gd0 {
    public final Object a;
    public final Object b;
    public final PointerInputEventHandler c;

    public tz0(Object obj, o30 o30Var, PointerInputEventHandler pointerInputEventHandler, int i) {
        o30Var = (i & 2) != 0 ? null : o30Var;
        this.a = obj;
        this.b = o30Var;
        this.c = pointerInputEventHandler;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new yz0(this.a, this.b, this.c);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof tz0) {
                tz0 tz0Var = (tz0) obj;
                if (o20.e(this.a, tz0Var.a) && o20.e(this.b, tz0Var.b) && this.c == tz0Var.c) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        yz0 yz0Var = (yz0) bd0Var;
        Object obj = yz0Var.s;
        Object obj2 = this.a;
        boolean z = true;
        boolean z2 = !o20.e(obj, obj2);
        yz0Var.s = obj2;
        Object obj3 = yz0Var.t;
        Object obj4 = this.b;
        if (!o20.e(obj3, obj4)) {
            z2 = true;
        }
        yz0Var.t = obj4;
        Class<?> cls = yz0Var.u.getClass();
        PointerInputEventHandler pointerInputEventHandler = this.c;
        if (cls == pointerInputEventHandler.getClass()) {
            z = z2;
        }
        if (z) {
            yz0Var.E0();
        }
        yz0Var.u = pointerInputEventHandler;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        Object obj = this.a;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        int i3 = i * 31;
        Object obj2 = this.b;
        if (obj2 != null) {
            i2 = obj2.hashCode();
        }
        return this.c.hashCode() + ((i3 + i2) * 961);
    }
}

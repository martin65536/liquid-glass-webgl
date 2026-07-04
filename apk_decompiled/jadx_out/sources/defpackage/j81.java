package defpackage;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j81 extends iq0 implements kv {
    public int g;
    public int h;
    public int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ jq k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j81(jq jqVar, ij ijVar) {
        super(ijVar);
        this.k = jqVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((j81) i((ij) obj2, (mv0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        j81 j81Var = new j81(this.k, ijVar);
        j81Var.j = obj;
        return j81Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int length;
        int i;
        Object j2Var;
        Node node = (Node) this.k.f;
        mv0 mv0Var = (mv0) this.j;
        int i2 = this.i;
        if (i2 != 0) {
            if (i2 == 1) {
                length = this.h;
                int i3 = this.g;
                o30.x(obj);
                i = i3 + 1;
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            length = node.getChildNodes().getLength();
            i = 0;
        }
        if (i < length) {
            node.getChildNodes().getLength();
            Node item = node.getChildNodes().item(i);
            if (item instanceof Element) {
                j2Var = new jq((Element) item);
            } else {
                item.getClass();
                j2Var = new j2(14, item);
            }
            this.j = mv0Var;
            this.g = i;
            this.h = length;
            this.i = 1;
            mv0Var.b(this, j2Var);
            return ik.e;
        }
        return x31.a;
    }
}

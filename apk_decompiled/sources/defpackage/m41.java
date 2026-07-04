package defpackage;

import org.w3c.dom.Node;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class m41 implements gv {
    public final /* synthetic */ int e;

    public /* synthetic */ m41(int i) {
        this.e = i;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        switch (this.e) {
            case 0:
                return new e7(((Float) obj).floatValue());
            case 1:
                return new e7(((Integer) obj).intValue());
            case 2:
                return Integer.valueOf((int) ((e7) obj).a);
            case 3:
                return new e7(((eo) obj).e);
            case 4:
                return new eo(((e7) obj).a);
            case 5:
                fo foVar = (fo) obj;
                return new f7(Float.intBitsToFloat((int) (foVar.a >> 32)), Float.intBitsToFloat((int) (4294967295L & foVar.a)));
            case 6:
                f7 f7Var = (f7) obj;
                float f = f7Var.a;
                float f2 = f7Var.b;
                return new fo((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
            case 7:
                mw0 mw0Var = (mw0) obj;
                return new f7(Float.intBitsToFloat((int) (mw0Var.a >> 32)), Float.intBitsToFloat((int) (4294967295L & mw0Var.a)));
            case 8:
                f7 f7Var2 = (f7) obj;
                float f3 = f7Var2.a;
                float f4 = f7Var2.b;
                return new mw0((Float.floatToRawIntBits(f4) & 4294967295L) | (Float.floatToRawIntBits(f3) << 32));
            case 9:
                ch0 ch0Var = (ch0) obj;
                return new f7(Float.intBitsToFloat((int) (ch0Var.a >> 32)), Float.intBitsToFloat((int) (4294967295L & ch0Var.a)));
            case 10:
                f7 f7Var3 = (f7) obj;
                float f5 = f7Var3.a;
                float f6 = f7Var3.b;
                return new ch0((Float.floatToRawIntBits(f6) & 4294967295L) | (Float.floatToRawIntBits(f5) << 32));
            case 11:
                long j = ((v10) obj).a;
                return new f7((int) (j >> 32), (int) (4294967295L & j));
            case 12:
                f7 f7Var4 = (f7) obj;
                return new v10((Math.round(f7Var4.b) & 4294967295L) | (Math.round(f7Var4.a) << 32));
            case 13:
                long j2 = ((c20) obj).a;
                return new f7((int) (j2 >> 32), (int) (4294967295L & j2));
            case 14:
                f7 f7Var5 = (f7) obj;
                int round = Math.round(f7Var5.a);
                int i = 0;
                if (round < 0) {
                    round = 0;
                }
                int round2 = Math.round(f7Var5.b);
                if (round2 >= 0) {
                    i = round2;
                }
                return new c20((round << 32) | (4294967295L & i));
            case 15:
                wo0 wo0Var = (wo0) obj;
                return new h7(wo0Var.a, wo0Var.b, wo0Var.c, wo0Var.d);
            case 16:
                h7 h7Var = (h7) obj;
                return new wo0(h7Var.a, h7Var.b, h7Var.c, h7Var.d);
            case 17:
                return Float.valueOf(((e7) obj).a);
            case 18:
                return ((l71) obj).g;
            case 19:
                return ((l71) obj).b;
            case 20:
                return ((l71) obj).f;
            case 21:
                return ((l71) obj).e;
            case 22:
                s71 s71Var = (s71) obj;
                s71Var.getClass();
                return s71Var;
            default:
                jq jqVar = (jq) obj;
                jqVar.getClass();
                String nodeName = ((Node) jqVar.f).getNodeName();
                nodeName.getClass();
                return Boolean.valueOf(nodeName.equals("item"));
        }
    }
}

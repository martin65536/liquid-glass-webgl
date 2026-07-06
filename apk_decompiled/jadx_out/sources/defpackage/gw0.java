package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gw0 {
    public static final gw0 e;
    public static final gw0 f;
    public static final gw0 g;
    public static final /* synthetic */ gw0[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [gw0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [gw0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [gw0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("START", 0);
        e = r0;
        ?? r1 = new Enum("STOP", 1);
        f = r1;
        ?? r3 = new Enum("STOP_AND_RESET_REPLAY_CACHE", 2);
        g = r3;
        h = new gw0[]{r0, r1, r3};
    }

    public static gw0 valueOf(String str) {
        return (gw0) Enum.valueOf(gw0.class, str);
    }

    public static gw0[] values() {
        return (gw0[]) h.clone();
    }
}
